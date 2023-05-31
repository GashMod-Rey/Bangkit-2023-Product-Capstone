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

// Login route
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

    // Check if the password is correct
    if (user.Password !== Password) {
      return res.status(401).json({ message: 'Invalid password.' });
    }

    // Successful login
    return res.status(200).json({ message: 'Login successful.' });
  });
});

app.get("/", (req, res) => {
    res.sendFile(__dirname + "/index.html");
});

// Start the server
app.listen(3000, () => {
  console.log('Server started on port 3000');
});
