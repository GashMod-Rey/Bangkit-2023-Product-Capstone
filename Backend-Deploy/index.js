const express = require("express");
const app = express();
const port = 8000;
const path = require("path");
const { Storage } = require("@google-cloud/storage");
const Multer = require("multer");
const src = path.join(__dirname, "views");
const UploadCV = Multer().single("pdfFile");
const UploadPP = Multer().single("ppFile");
const jwt = require('jsonwebtoken');
const session = require('express-session');
const timestamp = new Date();
const formattedTimestamp = timestamp.toISOString();
const cors = require('cors');
app.use(cors());

const bodyParser = require('body-parser');
const mysql = require('mysql');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(session({
    secret: 'yogaganteng',
    resave: false,
    saveUninitialized: true
  }));

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

const bucketCV = storage.bucket("bucket_pdf33");
const bucketPP = storage.bucket("bucket_pp33");

connection.connect((err) => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to MySQL database');
});

const secretKey = 'yogaganteng';
  
// Sign-up route
app.post('/signupApplicant', (req, res) => {
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

app.post('/setProfileApplicant', (req, res) => {
    // Extract the profile data from the request body
    const {name, dateOfBirth, email, language, summary, education, skills, salaryMin, location, degree, mobilePhone, openToWork } = req.body;
    const ppPath = req.session.ppPath;
    const pdfPath = req.session.pdfPath;
    const token = req.session.tokenA;
    const decodedToken = verifyToken(token);
    const username = decodedToken.username;

    const query = `UPDATE Applicants SET Name = ?, YearOfBirth = ?, Email = ?, Language = ?, Summary = ?, EducationInstitution = ?, Skills = ?, SalaryMinimum = ?, Location = ?, Degree = ?, MobilePhone = ?, OpenToWork = ?, PdfPath = ?, PpPath = ? WHERE Username = ?`;

    connection.query(query,[name, dateOfBirth, email, language, summary, education, skills, salaryMin, location, degree, mobilePhone, openToWork, pdfPath, ppPath, username],(err) => {
        if (err) {
            console.error('Error executing the query:', err);
            return res.status(500).json({ message: 'Internal server error.' });
        }

        return res.status(201).json({ message: 'Profile Updated' });
      }
    );
});

async function setProfileApplicantAuto(req, res) {
    // Extract the profile data from the request body
    const dataPy = req.session.dataPy;
    const ppPath = req.session.ppPath;
    const pdfPath = req.session.pdfPath;
    const token = req.session.tokenA;
    const decodedToken = verifyToken(token);
    const username = decodedToken.username;
    

    // data
    const name = dataPy.PERSON;
    const email = dataPy.EMAIL;
    const mobilePhone = dataPy.MOBILE;
    const summary = dataPy.SUM;
    const skills = dataPy.SKILL;
    const language = dataPy.LANG;
    const education = dataPy.EDU;
    const degree = dataPy.DEGREE;
    const location = dataPy.LOC;
    const skillString = skills.join(', ');
    const langString = language.join(', ');
    const joindegree = [].concat(...degree);
    const degreeString = joindegree.join(', ');

    const query = `UPDATE Applicants SET Name = ?, Email = ?, Language = ?, Summary = ?, EducationInstitution = ?, Skills = ?, Location = ?, Degree = ?, MobilePhone = ?, PdfPath = ? WHERE Username = ?`;

    connection.query(query,[name, email, langString, summary, education, skillString, location, degreeString, mobilePhone, pdfPath, username],(err) => {
        if (err) {
            console.error('Error executing the query:', err);
        }

        console.log('Profile Updated');
      }
    );
};

app.get('/getProfileApplicant', (req, res) => {
    const token = req.session.tokenA;
    const decodedToken = verifyToken(token);
    const username = decodedToken.username;

    const query = `SELECT * FROM Applicants WHERE Username = ?`;

    connection.query(query, [username], (err, results) => {
        if (err) {
          console.error('Failed to fetch applicants:', err);
          res.status(500).json({ error: 'Failed to fetch applicants' });
          return;
        }
        res.json(results);
    });
})

function hashPass(password){
    const hashpass = jwt.sign({password}, secretKey);
    return hashpass;
}

app.post('/loginApplicant', (req, res) => {
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
        req.session.tokenA = token;
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
        username: user.Username
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

const spawner = require("child_process").spawn;

app.get('/uploadCV', async (req, res) => {
    try {
      const [files] = await bucketCV.getFiles();
    
      if (files.length > 0) {
        const lastFile = files[files.length - 1];
        const url = `https://storage.googleapis.com/bucket_pdf33/${lastFile.id}`;
        const fileData = { id: lastFile.id, url };
        
        req.session.pdfPath = url;

        const data_to_pass_in = {
            data_sent: url
        }
        console.log("Data sent to python script ", data_to_pass_in);

        const python_process = spawner("python", ["./cvparser/CVParser.py", JSON.stringify(data_to_pass_in)]);

        python_process.stdout.on("data", async (data) => {
            const dataPy = JSON.parse(data.toString());
            req.session.dataPy = dataPy;
            console.log("Data from python script", JSON.parse(data.toString()));
            await setProfileApplicantAuto(req, res);
        });

        res.json(fileData);
        console.log('Success');
      } else {
        res.status(404).json({ error: 'No files found' });
      }
    } catch (error) {
      res.status(500).json({ error: 'Error: ' + error });
    }
});

app.post('/uploadCV', (req, res) => {
    console.log('Made it /upload');
    try {
        UploadCV(req, res, (err) => {
            if (err) {
                throw 'Error with PDF upload';
            }
            const file = req.file;
            if (!file) {
                throw 'No PDF file found';
            }

            // Handle the uploaded file
            const blob = bucketCV.file(`${formattedTimestamp}_post.pdf`);
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

app.post('/uploadPP', (req, res) => {
    console.log('Made it /upload');
    try {
      UploadPP(req, res, (err) => {
        if (err) {
          throw 'Error with profile picture upload';
        }
        const file = req.file;
        if (!file) {
          throw 'No profile picture file found';
        }
  
        // Handle the uploaded file
        const timestamp = Date.now();
        const fileName = `${timestamp}_profile.jpg`; // Set the desired file name and extension
        
        const blob = bucketPP.file(fileName);
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

app.get('/uploadPP', async (req, res) => {
    try {
      const [files] = await bucketPP.getFiles();
    
      if (files.length > 0) {
        const lastFile = files[files.length - 1];
        const url = `https://storage.googleapis.com/bucket_pp33/${lastFile.id}`;
        const fileData = { id: lastFile.id, url };
        
        req.session.ppPath = url;
        console.log(req.session.ppPath);

        res.json(fileData);
        console.log('Success');
      } else {
        res.status(404).json({ error: 'No files found' });
      }
    } catch (error) {
      res.status(500).json({ error: 'Error: ' + error });
    }
});
  

// app.post('/deleteCV', async (req, res) => {
//     const token = req.session.token;
//     const decodedToken = verifyToken(token);
//     const username = decodedToken.username;
  
//     try {
//         // Delete the file entry from MySQL
//         const deleteQuery = 'UPDATE Applicants SET PdfPath = ? WHERE Username = ?';
//         connection.query(deleteQuery, ["NULL", username], (deleteErr) => {
//           if (deleteErr) {
//             console.error('Error executing MySQL delete query:', deleteErr);
//             return res.status(500).json({ error: 'An error occurred while deleting the file entry' });
//           }
  
//           return res.json({ message: 'PDF file deleted successfully' });
//         });
//     } catch (err) {
//       console.error(err);
//       return res.status(500).json({ error: 'An error occurred while deleting the PDF file' });
//     }
// });

// Company API

//Sign up
app.post('/signupCompany', (req, res) => {
    const { username, password } = req.body;

    // Check if username and password are provided
    if (!username || !password) {
        return res.status(400).json({ message: 'Username and password are required.' });
    }

    // Check if the username already exists
    const checkQuery = `SELECT * FROM Company WHERE Username = ?`;
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
            const insertQuery = `INSERT INTO Company (Username, Password) VALUES (?, ?)`;
            connection.query(insertQuery, [username, hashPassword], (err) => {
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Sign-up successful.' });
        });
    });
});

app.post('/loginCompany', (req, res) => {
    const { Username, Password } = req.body;

    // Check if username and password are provided
    if (!Username || !Password) {
        return res.status(400).json({ message: 'Username and password are required.' });
    }

    // Query the database for the user
    const query = `SELECT * FROM Company WHERE Username = ?`;
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
        req.session.tokenC = token;
        return res.status(200).json({ token: token });
    });
});

app.post('/setProfileCompany', (req, res) => {
    // Extract the profile data from the request body
    const {name, summary, location, employee} = req.body;
    const token = req.session.tokenC;
    const decodedToken = verifyToken(token);
    const username = decodedToken.username;

    const query = `UPDATE Company SET Name = ?, Summary = ?, Location = ?, Employee = ? WHERE Username = ?`;

    connection.query(query,[name, summary, location, employee, username],(err) => {
        if (err) {
            console.error('Error executing the query:', err);
            return res.status(500).json({ message: 'Internal server error.' });
        }

        return res.status(201).json({ message: 'Profile Updated' });
      }
    );
});

app.get('/getProfileCompany', (req, res) => {
    const token = req.session.tokenC;
    const decodedToken = verifyToken(token);
    const username = decodedToken.username;

    const query = `SELECT * FROM Company WHERE Username = ?`;

    connection.query(query, [username], (err, results) => {
        if (err) {
          console.error('Failed to fetch applicants:', err);
          res.status(500).json({ error: 'Failed to fetch applicants' });
          return;
        }
        res.json(results);
    });
});

// API Relation

app.post('/offer', (req, res) => {
    const tokenA = req.session.tokenA;
    const tokenC = req.session.tokenC;
    const decodedTokenA = verifyToken(tokenA);
    const decodedTokenC = verifyToken(tokenC);
    const usernameA = decodedTokenA.username;
    const usernameC = decodedTokenC.username;

    const query = `INSERT INTO Relation (UsernameA, UsernameC) VALUES (?, ?)`;
    connection.query(query, [usernameA, usernameC], (err, results) =>{
        if (err) {
            console.error('Error executing the query:', err);
            return res.status(500).json({ message: 'Internal server error.' });
        }

        return res.status(201).json({ message: 'Success' });
    });
});

app.post('/offerResponse', (req, res) => {
    const tokenA = req.session.tokenA;
    const tokenC = req.session.tokenC;
    const decodedTokenA = verifyToken(tokenA);
    const decodedTokenC = verifyToken(tokenC);
    const usernameA = decodedTokenA.username;
    const usernameC = decodedTokenC.username;

    const { offer } = req.body;

    if(parseInt(offer) === 0){
        const query = `UPDATE Relation SET Offer = ?, Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
        connection.query(query, [offer, false, usernameA, usernameC], (err, results) =>{
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Success' });
        });    
    } else{
        const query = `UPDATE Relation SET Offer = ? WHERE UsernameA = ? AND UsernameC = ?`;
        connection.query(query, [offer, usernameA, usernameC], (err, results) =>{
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Success' });
        });
    }
});

app.post('/status', (req, res) => {
    const tokenA = req.session.tokenA;
    const tokenC = req.session.tokenC;
    const decodedTokenA = verifyToken(tokenA);
    const decodedTokenC = verifyToken(tokenC);
    const usernameA = decodedTokenA.username;
    const usernameC = decodedTokenC.username;

    const { status } = req.body;

    if(parseInt(status) === 1){
        const query = `UPDATE Relation SET Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
        connection.query(query, [status, usernameA, usernameC], (err, results) =>{
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }
        });

        const updateApplicant = `UPDATE Applicants SET OpenToWork = false WHERE Username = ?`; 
        connection.query(updateApplicant, [usernameA], (err, results) =>{
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Success update Applicants' });
        });
    } else {
        const query = `UPDATE Relation SET Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
        connection.query(query, [status, usernameA, usernameC], (err, results) =>{
            if (err) {
                console.error('Error executing the query:', err);
                return res.status(500).json({ message: 'Internal server error.' });
            }

            return res.status(201).json({ message: 'Success update Relation' });
        });
    }    
});
  
app.get("/", (req, res) => {
    res.sendFile(src + "/index.html");
});

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
});