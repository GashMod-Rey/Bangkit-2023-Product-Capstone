const express = require("express");
const app = express();
const port = 8000;
const path = require("path");
const { Storage } = require("@google-cloud/storage");
const Multer = require("multer");
const src = path.join(__dirname, "views");
app.use(express.static(src));

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

app.get("/upload", async (req, res) => {
    try {
      const [files] = await bucket.getFiles();
      res.send([files]);
      console.log("Success");
    } catch (error) {
      res.send("Error:" + error);
    }
  });

app.post("/upload", multer.single("pdfFile"), (req, res) => {
    console.log("Made it /upload");
    try{
        if (req.file){
            console.log("File found, trying to upload..");
            const blob = bucket.file(req.file.originalname);
            const blobStream = blob.createWriteStream();

            blobStream.on("finish", () => {
                res.setHeader("Content-Type", "application/json");
                res.status(200).json({ message: "Success" });

                //res.status(200).send("Success");
                console.log("Success");
            });
            blobStream.end(req.file.buffer);
        } else throw "error with pdf";
    } catch (error) {
        res.setHeader("Content-Type", "application/json");
        res.status(500).json({ error: error });
        //res.status(500).send(error);
    }
});


app.get("/", (req, res) => {
    res.sendFile(src + "/index.html");
});

app.listen(port, () => {
    console.log(`Server started on port ${port}`);
});