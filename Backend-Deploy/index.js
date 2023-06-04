const express = require("express");
const app = express();
const port = 8000;
const path = require("path");
const { Storage } = require("@google-cloud/storage");
const Multer = require("multer");
const src = path.join(__dirname, "views");
app.use(express.static(src));
const Upload = Multer().single("pdfFile");
const jwt = require('jsonwebtoken');


const bodyParser = require('body-parser');
const mysql = require('mysql');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// MySQL database connection
const connection = mysql.createConnection({
  host: '34.101.66.67',
  user: 'root',
  password: 'admindb33',
  database: 'hirehub'
});

const multer = Multer({
    storage: Multer.memoryStorage(),
    fileSize: 20 * 1024 * 1024, //ganti
});


let projectId = "capstone-project-hirehub";
let keyFilename = "capstone.json";

const storage = new Storage({
    projectId,
    keyFilename,
});

const bucket = storage.bucket("bucket_pdf33");

connection.connect((err) => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to MySQL database');
});

const secretKey = 'yogaganteng';
  
// Sign-up route
app.post('/signup', (req, res) => {
    const { username, password } = req.body;

    // Check if username and password are provided
    if (!username || !password) {
        return res.status(400).json({ message: 'Username and password are required.' });
    }

    // Check if the username already exists
    const checkQuery = `SELECT * FROM Applicants WHERE Username = ?`;
        connection.query(checkQuery, [username], (err, results) => {
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            if (results.length > 0) {
                return res.status(409).json({ message: 'Username already exists.' });
            }

            // Hashing
            const hashPassword = hashPass(password);

            // Insert the new user into the database
            const insertQuery = `INSERT INTO Applicants (Username, Password) VALUES (?, ?)`;
            connection.query(insertQuery, [username, hashPassword], (err) => {
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Sign-up successful.' });
        });
    });
});

function hashPass(password){
    const hashpass = jwt.sign({password}, secretKey);
    return hashpass;
}

app.post('/login', (req, res) => {
    const { Username, Password } = req.body;

    // Check if username and password are provided
    if (!Username || !Password) {
        return res.status(400).json({ message: 'Username and password are required.' });
    }

    // Query the database for the user
    const query = `SELECT * FROM Applicants WHERE Username = ?`;
    connection.query(query, [Username], (err, results) => {
        if (err) {
            console.error('Error executing the query:', err);
            return res.status(500).json({ message: 'Internal server error.' });
        }

        // Check if the user exists
        if (results.length === 0) {
            return res.status(401).json({ message: 'Invalid username.' });
        }

        const user = results[0];

        // Decode password
        const decoded = verifPass(user);

        // Check if the password is correct
        if (decoded.password !== Password) {
            return res.status(401).json({ message: 'Invalid password.' });
        }

        // Successful login
        const token = generateToken(user);
        return res.status(200).json({ token: token });
    });
});

// Verif pass
function verifPass(user){
    try {
        const decoded = jwt.verify(user.Password, secretKey);
        return decoded;
    } catch (error) {
        return null;
    }
}

//Generate a JWT token
function generateToken(user) {
    const payload = {
        username: user.username
        // Add any other relevant user data to the payload
    };

    // Sign the token with the secret key and set an expiration time
    const token = jwt.sign(payload, secretKey, { expiresIn: '1h' });
    return token;
}
  
// Verify and decode the JWT token
function verifyToken(token) {
    try {
        const decoded = jwt.verify(token, secretKey);
        return decoded;
    } catch (error) {
        // Token verification failed
        return null;
    }
}
  
// Protected route example
app.get('/protected', (req, res) => {
    // Get the token from the request header or query parameter
    const token = req.headers.authorization || req.query.token;

    if (!token) {
        return res.status(401).json({ message: 'Access denied. No token provided.' });
    }

    // Verify the token
    const decoded = verifyToken(token);
    if (decoded) {
        // Token is valid, user is authenticated
        return res.status(200).json({ message: 'Access granted to protected resource.' });
    } else {
        // Token is invalid or expired
        return res.status(401).json({ message: 'Access denied. Invalid token.' });
    }
});

app.get("/upload", async (req, res) => {
    try {
      const [files] = await bucket.getFiles();
      res.send([files]);
      console.log("Success");
    } catch (error) {
      res.send("Error:" + error);
    }
});

let id = 0;

app.post('/upload', (req, res) => {
    console.log('Made it /upload');
    try {
        Upload(req, res, (err) => {
            if (err) {
                throw 'Error with PDF upload';
            }
            const file = req.file;
            if (!file) {
                throw 'No PDF file found';
            }

            // Handle the uploaded file
            const blob = bucket.file(`${id}_post.pdf`);
            const blobStream = blob.createWriteStream();

            blobStream.on('finish', () => {
            res.setHeader('Content-Type', 'application/json');
            res.status(200).json({ message: 'Success' });
            console.log('Success');
            });

            blobStream.end(file.buffer);
        });
    } catch (error) {
        res.setHeader('Content-Type', 'application/json');
        res.status(500).json({ error });
    }
});
  
app.get("/", (req, res) => {
    res.sendFile(src + "/index.html");
});

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
});