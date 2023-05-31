const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// MySQL database connection
const connection = mysql.createConnection({
  host: '34.101.66.67',
  user: 'root',
  password: 'admindb33',
  database: 'hirehub'
});

connection.connect((err) => {
  if (err) {
    console.error('Error connecting to the database:', err);
    return;
  }
  console.log('Connected to MySQL database');
});

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

    // Insert the new user into the database
    const insertQuery = `INSERT INTO Applicants (Username, Password) VALUES (?, ?)`;
    connection.query(insertQuery, [username, password], (err) => {
      if (err) {
        console.error('Error executing the query:', err);
        return res.status(500).json({ message: 'Internal server error.' });
      }

      return res.status(201).json({ message: 'Sign-up successful.' });
    });
  });
});

app.get("/", (req, res) => {
  res.sendFile(__dirname + "/index.html");
});

// Start the server
app.listen(3000, () => {
  console.log('Server started on port 3000');
});
