const express = require("express");
const app = express();
const port = 8000;
const { Storage } = require("@google-cloud/storage");
const Multer = require("multer");
const UploadCV = Multer().single("pdfFile");
const jwt = require("jsonwebtoken");
const session = require("express-session");
const timestamp = new Date();
const formattedTimestamp = timestamp.toISOString();
const cors = require("cors");
const spawner = require("child_process").spawn;
app.use(cors());
const axios = require("axios");

const bodyParser = require("body-parser");
const mysql = require("mysql");
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(
  session({
    secret: "yogaganteng",
    resave: false,
    saveUninitialized: true,
  })
);

// MySQL database connection
const connection = mysql.createConnection({
  host: "34.101.66.67",
  user: "root",
  password: "admindb33",
  database: "hirehub",
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

connection.connect((err) => {
  if (err) {
    console.error("Error connecting to the database:", err);
    return;
  }
  console.log("Connected to MySQL database");
});

const secretKey = "yogaganteng";

// Sign-up route
app.post("/signupApplicant", (req, res) => {
  const { username, password } = req.body;

  // Check if username and password are provided
  if (!username || !password) {
    return res.status(400).json({ message: "Username and password are required." });
  }

  // Check if the username already exists
  const checkQuery = `SELECT * FROM Applicants WHERE Username = ?`;
  connection.query(checkQuery, [username], (err, results) => {
    if (err) {
      console.error("Error executing the query:", err);
      return res.status(500).json({ message: "Internal server error." });
    }

    if (results.length > 0) {
      return res.status(409).json({ message: "Username already exists." });
    }

    // Hashing
    const hashPassword = hashPass(password);

    // Insert the new user into the database
    const insertQuery = `INSERT INTO Applicants (Username, Password) VALUES (?, ?)`;
    connection.query(insertQuery, [username, hashPassword], (err) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Sign-up successful." });
    });
  });
});

let tokenglobalA = "";
app.post("/loginApplicant", (req, res) => {
  const { Username, Password } = req.body;

  // Check if username and password are provided
  if (!Username || !Password) {
    return res.status(400).json({ message: "Username and password are required." });
  }

  // Query the database for the user
  const query = `SELECT * FROM Applicants WHERE Username = ?`;
  connection.query(query, [Username], (err, results) => {
    if (err) {
      console.error("Error executing the query:", err);
      return res.status(500).json({ message: "Internal server error." });
    }

    // Check if the user exists
    if (results.length === 0) {
      return res.status(401).json({ message: "Invalid username." });
    }

    const user = results[0];

    // Decode password
    const decoded = verifPass(user);

    // Check if the password is correct
    if (decoded.password !== Password) {
      return res.status(401).json({ message: "Invalid password." });
    }

    // Successful login
    const token = generateToken(user);
    tokenglobalA = token;
    return res.status(200).json({ token: token });
  });
});

function authenticateTokenA(req, res, next) {
  const token = tokenglobalA;

  if (token == null) {
    return res.status(401).json({ message: "Unauthorized. Please provide a valid token." });
  }

  jwt.verify(token, secretKey, (err, applicant) => {
    if (err) {
      console.error("Error verifying the token:", err);
      return res.status(403).json({ message: "Forbidden. Invalid token." });
    }
    req.applicant = {
      username: applicant.username,
      // Add any other relevant user data from the token
    };
    next();
  });
}
function authenticateTokenC(req, res, next) {
  const token = tokenglobalC;

  if (token == null) {
    return res.status(401).json({ message: "Unauthorized. Please provide a valid token." });
  }

  jwt.verify(token, secretKey, (err, company) => {
    if (err) {
      console.error("Error verifying the token:", err);
      return res.status(403).json({ message: "Forbidden. Invalid token." });
    }
    req.company = {
      username: company.username,
      // Add any other relevant user data from the token
    };
    next();
  });
}

// hashing password
function hashPass(password) {
  const hashpass = jwt.sign({ password }, secretKey);
  return hashpass;
}

// Verif pass
function verifPass(user) {
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
    username: user.Username,
    // Add any other relevant user data to the payload
  };

  // Sign the token with the secret key and set an expiration time
  const token = jwt.sign(payload, secretKey, { expiresIn: "1h" });
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

app.post("/setProfileApplicant", authenticateTokenA, (req, res) => {
  // Extract the profile data from the request body
  const { name, dateOfBirth, email, language, summary, education, skills, salaryMin, location, degree, mobilePhone, openToWork } = req.body;
  const pdfPath = req.session.pdfPath;
  const username = req.applicant.username;

  const query = `UPDATE Applicants SET Name = ?, YearOfBirth = ?, Email = ?, Language = ?, Summary = ?, EducationInstitution = ?, Skills = ?, SalaryMinimum = ?, Location = ?, Degree = ?, MobilePhone = ?, OpenToWork = ?, PdfPath = ? WHERE Username = ?`;

  connection.query(query, [name, dateOfBirth, email, language, summary, education, skills, salaryMin, location, degree, mobilePhone, openToWork, pdfPath, username], (err) => {
    if (err) {
      console.error("Error executing the query:", err);
      res.status(500).json({ error: "Error: " + err });
    }
    res.status(200).json({ message: "Success" });
  });
});

app.get("/getProfile", authenticateTokenA, (req, res) => {
  const username = req.applicant.username;

  const query = `SELECT * FROM Applicants WHERE Username = ?`;

  connection.query(query, [username], (err, results) => {
    if (err) {
      console.error("Failed to fetch applicants:", err);
      res.status(500).json({ error: "Failed to fetch applicants" });
      return;
    }
    const data = results[0];
    //res.json(results);
    res.json(data);
  });
});

async function setProfileApplicantAuto(req, res) {
  // Extract the profile data from the request body
  const dataPy = req.session.dataPy;
  const pdfPath = req.session.pdfPath;
  const username = req.applicant.username;

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
  const skillString = skills.join(", ");
  const langString = language.join(", ");
  const joindegree = [].concat(...degree);
  const degreeString = joindegree.join(", ");

  const query = `UPDATE Applicants SET Name = ?, Email = ?, Language = ?, Summary = ?, EducationInstitution = ?, Skills = ?, Location = ?, Degree = ?, MobilePhone = ?, PdfPath = ? WHERE Username = ?`;

  connection.query(query, [name, email, langString, summary, education, skillString, location, degreeString, mobilePhone, pdfPath, username], (err) => {
    if (err) {
      console.error("Error executing the query:", err);
      res.status(500).json({ error: "Error: " + err });
    }

    res.status(200).json({ message: "Profile Updated" });
  });
}

async function getPDF(req, res) {
  try {
    const [files] = await bucketCV.getFiles();

    if (files.length > 0) {
      const lastFile = files[files.length - 1];
      const url = `https://storage.googleapis.com/bucket_pdf33/${lastFile.id}`;
      const fileData = { id: lastFile.id, url };

      req.session.pdfPath = url;

      const data_to_pass_in = {
        data_sent: url,
      };

      console.log(url);

      //const python_process = spawner("python", ["./cvparser/CVParser.py", JSON.stringify(data_to_pass_in)]);

      const response = await axios.post('http://34.101.252.239:8080/parsedata', { PdfPath: url });
      console.log(response);
      const data = response.data;
      console.log("Data from python script", data);

      req.session.dataPy = data;
      await setProfileApplicantAuto(req, res);

      console.log("Success");
    }
  } catch (error) {
    console.log(error);
  }
}

app.post("/uploadCV", authenticateTokenA, async (req, res) => {
  console.log("Made it /upload");
  try {
    UploadCV(req, res, (err) => {
      if (err) {
        throw "Error with PDF upload";
      }
      const file = req.file;
      if (!file) {
        throw "No PDF file found";
      }

      // Handle the uploaded file
      const blob = bucketCV.file(`${formattedTimestamp}_post.pdf`);
      const blobStream = blob.createWriteStream();

      blobStream.on("finish", async () => {
        console.log("Success");
        await getPDF(req, res);
      });

      blobStream.end(file.buffer);
    });
  } catch (error) {
    console.log(error);
  }
});

// Company API

//Sign up
app.post("/signupCompany", (req, res) => {
  const { username, password } = req.body;

  // Check if username and password are provided
  if (!username || !password) {
    return res.status(400).json({ message: "Username and password are required." });
  }

  // Check if the username already exists
  const checkQuery = `SELECT * FROM Company WHERE Username = ?`;
  connection.query(checkQuery, [username], (err, results) => {
    if (err) {
      console.error("Error executing the query:", err);
      return res.status(500).json({ message: "Internal server error." });
    }

    if (results.length > 0) {
      return res.status(409).json({ message: "Username already exists." });
    }

    // Hashing
    const hashPassword = hashPass(password);

    // Insert the new user into the database
    const insertQuery = `INSERT INTO Company (Username, Password) VALUES (?, ?)`;
    connection.query(insertQuery, [username, hashPassword], (err) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Sign-up successful." });
    });
  });
});

let tokenglobalC = "";
app.post("/loginCompany", (req, res) => {
  const { Username, Password } = req.body;

  // Check if username and password are provided
  if (!Username || !Password) {
    return res.status(400).json({ message: "Username and password are required." });
  }

  // Query the database for the user
  const query = `SELECT * FROM Company WHERE Username = ?`;
  connection.query(query, [Username], (err, results) => {
    if (err) {
      console.error("Error executing the query:", err);
      return res.status(500).json({ message: "Internal server error." });
    }

    // Check if the user exists
    if (results.length === 0) {
      return res.status(401).json({ message: "Invalid username." });
    }

    const user = results[0];

    // Decode password
    const decoded = verifPass(user);

    // Check if the password is correct
    if (decoded.password !== Password) {
      return res.status(401).json({ message: "Invalid password." });
    }

    // Successful login
    const token = generateToken(user);
    tokenglobalC = token;
    return res.status(200).json({ token: token });
  });
});

app.post("/setProfileCompany", authenticateTokenC, (req, res) => {
  // Extract the profile data from the request body
  const { name, summary, location, employee } = req.body;
  const username = req.company.username;

  const query = `UPDATE Company SET Name = ?, Summary = ?, Location = ?, Employee = ? WHERE Username = ?`;

  connection.query(query, [name, summary, location, employee, username], (err) => {
    if (err) {
      console.error("Error executing the query:", err);
      res.status(500).json({ error: "Error: " + err });
    }
    res.status(200).json({ message: "Profile Updated" });
  });
});

app.get("/getProfileCompany", authenticateTokenC, (req, res) => {
  const username = req.company.username;

  const query = `SELECT * FROM Company WHERE Username = ?`;

  connection.query(query, [username], (err, results) => {
    if (err) {
      console.error("Failed to fetch applicants:", err);
      res.status(500).json({ error: "Failed to fetch applicants" });
      return;
    }
    const data = results[0];
    //res.json(results);
    res.json(data);
  });
});

// API Relation

app.post("/offer", authenticateTokenA, authenticateTokenC, (req, res) => {
  const usernameA = req.applicant.username;
  const usernameC = req.company.username;

  const query = `INSERT INTO Relation (UsernameA, UsernameC) VALUES (?, ?)`;
  connection.query(query, [usernameA, usernameC], (err, results) => {
    if (err) {
      console.error("Error executing the query:", err);
      return res.status(500).json({ message: "Internal server error." });
    }

    return res.status(201).json({ message: "Success" });
  });
});

app.post("/offerResponse", authenticateTokenA, authenticateTokenC, (req, res) => {
  const usernameA = req.applicant.username;
  const usernameC = req.company.username;

  const { offer } = req.body;

  if (parseInt(offer) === 0) {
    const query = `UPDATE Relation SET Offer = ?, Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
    connection.query(query, [offer, false, usernameA, usernameC], (err, results) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Success" });
    });
  } else {
    const query = `UPDATE Relation SET Offer = ? WHERE UsernameA = ? AND UsernameC = ?`;
    connection.query(query, [offer, usernameA, usernameC], (err, results) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Success" });
    });
  }
});

app.post("/status", authenticateTokenA, authenticateTokenC, (req, res) => {
  const usernameA = req.applicant.username;
  const usernameC = req.company.username;

  const { status } = req.body;

  if (parseInt(status) === 1) {
    const query = `UPDATE Relation SET Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
    connection.query(query, [status, usernameA, usernameC], (err, results) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }
    });

    const updateApplicant = `UPDATE Applicants SET OpenToWork = false WHERE Username = ?`;
    connection.query(updateApplicant, [usernameA], (err, results) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Success update Applicants" });
    });
  } else {
    const query = `UPDATE Relation SET Status = ? WHERE UsernameA = ? AND UsernameC = ?`;
    connection.query(query, [status, usernameA, usernameC], (err, results) => {
      if (err) {
        console.error("Error executing the query:", err);
        return res.status(500).json({ message: "Internal server error." });
      }

      return res.status(201).json({ message: "Success update Relation" });
    });
  }
});

// API Chat
const http = require('http').Server(app); 
const io = require('socket.io')(http); 
var userList = []; 
var roomChatList = [];

// Middleware to parse JSON request bodies
app.use(express.json());

// Endpoint for retrieving all messages
async function getMessageApplicant(req, res) {
  res.json({
    message: "Success Chat to Company from Applicant",
  });
  console.log({ message: "Success Chat to Company from Applicant" });
}

async function getMessageCompany(req, res) {
  res.json({
    message: "Success Chat to Applicant from Company",
  });
  console.log({ message: "Success Chat to Applicant from Company" });
}

// Endpoint for retrieving all chat
async function getChat(req, res) {
  res.json({ message: "Success Created Room Chat" });
  console.log({ message: "Success Created Room Chat" });
}

// Endpoint for create new chat
app.post("/api/chat/newchat", authenticateTokenA, authenticateTokenC, async (req, res) => {
  // Create a new Date object
  const currentDate = new Date();
  const ChatUsernameA = req.applicant.username;
  const ChatUsernameC = req.company.username;
  const RoomId = `RoomChat_` + ChatUsernameA + ChatUsernameC;

  // Specify the options for formatting the date
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    timeZone: "Asia/Jakarta", // Set the desired time zone
  };

  // Format the date into the SQL timestamp format
  const timestamp = currentDate
    .toLocaleString("id-ID", options)
    .replace(/\//g, "-")
    .replace(",", "")
    .replace(/\./g, ":")
    .replace(/(\d{2})-(\d{2})-(\d{4})/, "$3-$2-$1");

  // Insert the new chat into the MySQL database
  const query = "INSERT INTO Room_Chat (Id, ChatUsernameA, ChatUsernameC, Created_at) VALUES (?, ?, ?, ?)";
  const values = [RoomId, ChatUsernameA, ChatUsernameC, timestamp];

  connection.query(query, values, async (error, result) => {
    if (error) {
      console.log(error)
      return res.status(500).json({ error: "Failed to create chat" });
    }
    await getChat(req, res);
  });
});

// Endpoint for sending a new message from applicant
app.post("/api/messages/sendfromapplicant", authenticateTokenA, authenticateTokenC, async (req, res) => {
  // Create a new Date object
  const currentDate = new Date();
  const ChatUsernameA = req.applicant.username;
  const ChatUsernameC = req.company.username;
  const { Message } = req.body;

  // Specify the options for formatting the date
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    timeZone: "Asia/Jakarta", // Set the desired time zone
  };

  // Format the date into the SQL timestamp format
  const timestamp = currentDate
    .toLocaleString("id-ID", options)
    .replace(/\//g, "-")
    .replace(",", "")
    .replace(/\./g, ":")
    .replace(/(\d{2})-(\d{2})-(\d{4})/, "$3-$2-$1");

  const getRoomIdQuery = "SELECT Id FROM Room_Chat WHERE ChatUsernameA = ? AND ChatUsernameC = ?";
  const getRoomIdValues = [ChatUsernameA, ChatUsernameC];

  connection.query(getRoomIdQuery, getRoomIdValues, (error, rows) => {
    if (error) {
      console.error("Error retrieving RoomId:", error);
      return res.status(500).json({ error: "Failed to insert message" });
    }

    if (rows.length === 0) {
      return res.status(400).json({ error: "Invalid chat participants" });
    }

    const RoomId = rows[0].Id;

    const insertMessageQuery = "INSERT INTO Chat_Applicant (RoomId, ApplicantUsername, Message, Sent_at) VALUES (?, ?, ?, ?)";
    const insertMessageValues = [RoomId, ChatUsernameA, Message, timestamp];

    connection.query(insertMessageQuery, insertMessageValues, async (error, result) => {
      if (error) {
        console.error("Error inserting message:", error);
        return res.status(500).json({ error: "Failed to insert message" });
      }

      await getMessageApplicant(req, res);
    });
  });
});

// Endpoint for sending a new message from company
app.post("/api/messages/sendfromcompany", authenticateTokenA, authenticateTokenC, async (req, res) => {
  // Create a new Date object
  const currentDate = new Date();
  const ChatUsernameA = req.applicant.username;
  const ChatUsernameC = req.company.username;
  const { Message } = req.body;

  // Specify the options for formatting the date
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    timeZone: "Asia/Jakarta", // Set the desired time zone
  };

  // Format the date into the SQL timestamp format
  const timestamp = currentDate
    .toLocaleString("id-ID", options)
    .replace(/\//g, "-")
    .replace(",", "")
    .replace(/\./g, ":")
    .replace(/(\d{2})-(\d{2})-(\d{4})/, "$3-$2-$1");

  const getRoomIdQuery = "SELECT Id FROM Room_Chat WHERE ChatUsernameA = ? AND ChatUsernameC = ?";
  const getRoomIdValues = [ChatUsernameA, ChatUsernameC];

  connection.query(getRoomIdQuery, getRoomIdValues, (error, rows) => {
    if (error) {
      console.error("Error retrieving RoomId:", error);
      return res.status(500).json({ error: "Failed to insert message" });
    }

    if (rows.length === 0) {
      return res.status(400).json({ error: "Invalid chat participants" });
    }

    const RoomId = rows[0].Id;

    const insertMessageQuery = "INSERT INTO Chat_Company (RoomId, CompanyUsername, Message, Sent_at) VALUES (?, ?, ?, ?)";
    const insertMessageValues = [RoomId, ChatUsernameC, Message, timestamp];

    connection.query(insertMessageQuery, insertMessageValues, async (error, result) => {
      if (error) {
        console.error("Error inserting message:", error);
        return res.status(500).json({ error: "Failed to insert message" });
      }

      await getMessageCompany(req, res);
    });
  });
});

// Recommender System
// const datafilter = [{ ageFilter: [23, 27], tolerance: 5, skillFilter: ["C", "C++", "Java"], langFilter: ["English", "Mandarin", "Javanese"], salaryFilter: [3, 12], tol: 1 }];
function getApplicantsData(location) {
  return new Promise((resolve, reject) => {
    const query = "SELECT * FROM Applicants WHERE Location = ?";

    connection.query(query, [location], (error, results) => {
      if (error) {
        console.error("Error retrieving applicants:", error);
        reject(error);
      } else {
        resolve(results);
      }
    });
  });
}

// Run the Python code and pass the data as command-line arguments
const runPythonCode = async ([applicantsData, datafilter], res) => {
  try {
    //const pythonProcess = spawner("python", ["../script/Scoring.py", JSON.stringify(applicantsData), JSON.stringify(datafilter)]);
    const appData = JSON.stringify(applicantsData);
    const filter = JSON.stringify(datafilter);
    const sendData = {appData, filter};
    const response = await axios.post('http://34.101.38.231:5000/recommender', { Data: sendData });
    const data = response.data;
    console.log(data);
    
    try {
      for (let i = 0; i < data.length; i++) {
        applicantsData[i]["Score"] = data[i];
      }
      console.log(applicantsData);

      // Convert to JSON format and send it to the frontend
      res.json(applicantsData);
    } catch (error) {
      console.error("Error:", error);
      // Handle error response to the frontend
      res.status(500).json({ error: "An error occurred" });
    }

  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  }
};

// Express.js endpoint to receive data from mobile frontend
app.post('/api/filter', (req, res) => {
  const { ageFilter, tolerance, skillFilter, langFilter, salaryFilter, tol, location } = req.body;
  // Convert ageFilter from string to a list
  const parsedAgeFilter = JSON.parse(ageFilter).map(Number);
  const parsedSalaryFilter = JSON.parse(salaryFilter).map(Number);
  
  const datafilter = [
    {
      ageFilter : [parsedAgeFilter[0], parsedAgeFilter[1]],
      tolerance : parseInt(tolerance),
      skillFilter: skillFilter,
      langFilter: langFilter,
      salaryFilter : [parsedSalaryFilter[0], parsedSalaryFilter[1]],
      tol : parseFloat(tol)
    }
  ];

  // Retrieve the data of applicants and run the Python code
  getApplicantsData(location)
    .then((applicantsData) => {
      runPythonCode([applicantsData, datafilter], res); // Pass the `res` object as an argument
    })
    .catch((error) => {
      console.error('Error:', error);
      res.sendStatus(500);
    });
});
io.on('connection', function(socket) {
  console.log('User Connection');

  socket.on("message", function(value) {
      console.log(value);
      io.emit("message", value);
  });

  socket.on("user-join", function(value) {
      console.log(value + "user-join");
      socket.broadcast.emit("new-users", value);
  });

  socket.on('connect user', function(id,user){
      console.log("Connected user ");
      io.emit('connect user', id,user);
  });

  socket.on('on typing', function(id,typing){
      io.emit('on typing', id,typing);
  });

  socket.on('chat message', function(id,msg){
      io.emit('chat message', id, msg);
  });

  socket.on('allUser', function(token){
      io.emit('allUser', userList);
  });

  socket.on('allRoomChat', function(token) {
      io.emit('allRoomChat', roomChatList);
  });

  socket.on('SingUp', function(username,User){
      for (let i = 0; i < userList.length; i++) { 
         if (userList[i]['username']==User['username']) {
            io.emit('SingUp', username,false);
             break;
         }else if(i==userList.length-1){
            userList.push(User);
            io.emit('SingUp', username,true);
            io.emit('allUser', userList);    
         }
       }
      if(userList.length==0){
            userList.push(User);
            io.emit('SingUp', id,true);
            io.emit('get all user', userList);
         }
   });

   socket.on('SingOut', function(username, User){
      userList = userList.filter((user) => user['token'] != User['token']);
      io.emit('SingOut', username,false);
      io.emit('allUser', userList);  
   });

  socket.on('SingIn', function(username, User) {
      console.log(userList.length)
      userList.push(User)
      console.log(userList);
      
      console.log(User["username"])
      console.log(username)
      for (let i = 0; i < userList.length; i++) {
          if (userList[i]['username'] == User['username'] && userList[i]['token'] == User['token']) {
              userList[i]['isOnline'] = User['isOnline'];
              io.emit('SingIn', username, userList[i]);
              break;
          }
      }
  });

  socket.on('dataUpdate', function(User){ 
      console.log("dataUpdate " + User["username"] + " " + User["isOnline"])
      console.log(userList)
      for (let i = 0; i < userList.length; i++) { 
        if (userList[i]['username'] == User['username']) {
          userList[i]['isOnline'] = User['isOnline'];
           io.emit('get all user', userList);
           break;
        }
      }
  });  
})

http.listen(port, function() {
  console.log('Server started on port ' + port);
});

// app.listen(port, () => {
//   console.log(`Server started on port ${port}`);
// });
