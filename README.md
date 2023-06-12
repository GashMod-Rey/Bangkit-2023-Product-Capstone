# HireHub
<br/>
<div align="center">
  <img src="logo.png">
</div>
<div align="center">
  This project was made by team SOLID.exe [C23-PS241] Bangkit Academy 2023 H1. 
</div>
<br/>

| Name                    | Bangkit ID    | Learning Path      |
| ----------------------- |---------------| -------------------|
| Reynard Matthew Yaputra | M264DSX1826   | Machine Learning   |
| Hanny Putri Gayatri     | M346DSY3464   | Machine Learning   |
| Nadya Angelia           | A346DSY2036   | Mobile Development |
| Gianzra Andelle Basae   | A346DKX4498   | Mobile Development |
| Yoga Aditiya            | C264DSX2386   | Cloud Computing    |
| Nehemia Gueldi          | C264DSX2229   | Cloud Computing    |

HireHub was inspired by the fact that companies still rely on their HR to filter thousands of job applicants’ CVs. This process is time-consuming and labor-intensive. On the other hand, applicants usually have to make their online in-app profile from scratch which leads to inconsistency between their CV and online profile. Therefore, we offer this application to tackle the problem with recommenders for the company and CV text processing to generate online profiles automatically. With this application, HR will spend less time dealing with irrelevant applicants and the applicants will be helped in making their online presence in-app.

HireHub is a talent search and recruitment platform that connects job seekers with potential employers. This application allows job seekers to create an online profile as simply as uploading their resume and wait for the company to offer them any related job. Employers can search for the candidate and our recommenders system in-app will help them to find the best candidate for specified criteria. This application also offers messaging feature between employers and candidates to enhance the recruitment experience. HireHub aims to surpass beyond nowadays recruitment process with some features, such as CV processing to create online profiles automatically and the recommenders system to help the company with filtering suitable candidates for specified positions.

Hirehub has two user-level, the applicants and the companies. Firstly, the applicants and companies should register and log into the application. Then, each user level has its own functionalities. Applicants can drop their CVs/resumes in our application for companies’ HR to evaluate. In our competitor apps, when applicants want to make their profile, they should do it from scratch and manually input all of their data. Thus, we introduce the second usage of applicants' CVs which is to parse their resume and get the information needed to make their profile, such as name, location (country), mobile phone number, email, skills, languages, education institution, education degree, and their professional summary. However, we still have the feature for manual editing/input since some attributes might not or never be present in a CV/resume, such as age/birth date/birth year and salary expectation or our model badly parse the resume. After applicants drop their resumes and make online profiles, they just need to wait until company offers come and they have the right to accept or reject them. They can also chat with the company during the recruitment process.

On the other hand, company user-level can make their profile online manually. They can also search for applicants/talents with simple steps, such as setting the filter (filtering age/skills/other) and then searching. Then, our application will recommend (with the recommender system that we develop) some applicants in a list from the most relevant to the filter all the way down to the most irrelevant to the filter. HR can then see their online profile and CV, then can offer the applicants a job. HR needs to wait for applicants' responses and if applicants accept the offer for recruitment, the recruitment process will be facilitated via chat in our application. Then, the company will have the right to cancel the recruitment process (since the applicants are no longer relevant, mismatch in the interview/technical test, etc.) or accept the applicants to their companies. If the applicants are accepted by the company, then they will disappear from the search list and the running recruitment process will be terminated.

## 1. Technical Stack
This application was built using technical stack explained below.
<ol>
  <li> Machine Learning: 
    <ul>
      <li> Main Programming Language&nbsp; : Python </li>
      <li> IDEs&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; : Jupyter Notebook & Visual Studio Code </li>
      <li> Libraries&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;: NLTK, SpaCy, string, requests, fitz, re, time, tqdm, sys, json, sklearn, tensorflow, keras, pathlib, warnings </li>
      <li> Pre-built model&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; : en_core_web_sm & en_core_web_md </li>
    </ul>
  </li>
  <li> Mobile Development: 
    <ul>
      <li> Main Scripting/Programming Language : Kotlin & XML </li>
      <li> IDE&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; : Android Studio </li>
      <li> Library&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; : Retrofit, Glide, Datastore, TensorFlow Lite, Socket.io, GSON, Dexter, Room Database, CircleImageView, LiveData, ViewModel </li>
    </ul>
  </li>
  <li> Cloud Computing: 
    <ul>
      <li> Main Programming Language : NodeJS & SQL (MySQL) </li>
      <li> IDE&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;: Visual Studio Code </li>
      <li> Framework&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;: ExpressJS </li>
      <li> Libraries&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&nbsp;: Multer, @google-cloud/storage, jsonwebtoken, express-session, cors</li>
      <li> Services&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;: Google Cloud Services </li>
    </ul>
  </li>
</ol>

## 2. Machine Learning Description and Data Usage

<b> New Findings&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp; : model to detect academic degree in /script/output/model-best, three entities content-based filtering (in RecommenderSystem.ipynb, Experimental Work section), and two JSONL files to detect country/languages in a CV </b>

There are three main workloads for Machine Learning team, namely searching for PDF-formatted CV dataset, building a model that can recognize entity from CV/Resume (entity: name, location, education institution, education degree, email, mobile phone, skills, languages, and professional summary), and building a recommendation system for filtering applicants in HR/companies-side.
This application used PDF-formatted CV data from various source. For Machine Learning training purposes, we used data from.
<ol>
  <li>Resume Dataset - [URL: https://www.kaggle.com/datasets/aishikai/resume-dataset]</li>
  Details <br/>
  Owner&emsp;&emsp;&ensp;: Aishik Rakshit <br/>
  Content&emsp;&emsp;: Collection of Curriculum Vitae files <br/>
  Formats&emsp;&emsp;: PDF and Microsoft Word <br/>
  Language&emsp;&nbsp;: Mostly English <br/>
  Total files&emsp;&nbsp; : 3264 (PDF File), 2296 (Word File) <br/>
  Format used : Only PDF (All of the files) <br/>
  <br/>
  Additional Information <br/>
  -Most of the CVs data are from Indians <br/>
  -Data collected from various random websites <br/>
  -Kaggle Usability Rating 2.50 <br/>
  <br/>
  <li>Resume Data with Annotations - [URL: https://www.kaggle.com/datasets/extremelysunnyyk/resume-data-with-annotations]</li>
  Details <br/>
  Owner&emsp;&emsp;&ensp; : Yong Kang Chia <br/>
  Content&emsp;&emsp;: CV files in PDF and Microsoft Word format, Poster files, and Training Data files <br/>
  Language&emsp; : English <br/>
  Total files&emsp;&ensp;: 8 (PDF File), 2 (Word File), 1 (pkl File), 2 (txt file) <br/>
  Format used : Only PDF (5 Files) <br/>
  <br/>
  Additional Information <br/>
  -Kaggle Usability Rating 3.13 <br/>
  <br/>
  <li>Team Member CVs'</li>
  Details <br/>
  Owner : Reynard Matthew Yaputra, Nadya Angelia, Andelle Gianzra Basae
</ol>
After that, we built a CV Parser with various libraries and methods, such as.
<ol>
  <li>Regex (Regular Expression): Used in getting email, mobile phone, name, and education institution.</li>
  <li>Cleaning CV data (removing unnecessary part for gathering certain entity): Used in getting all the entity, significantly shown in getting name, education institution, and summary.</li>
  <li>Using en_core_web_sm model: Used in getting name.</li>
  <li>Using en_core_web_md model: Used in getting name, skills, languages, and location.</li>
  <li>Using pre-defined rule for model's pipe: Used in getting skills (from JobZilla AI, jz_skill_patterns.jsonl)</li>
  <li>Using our own new rule for model's pipe: Used in getting languages and location (hh_lang_pattern.jsonl and hh_country_pattern.jsonl)</li>
  <li>Using our own new model created with SpaCy: Used in getting education degree.</li>
</ol>

Lastly, we built a recommendation system with TensorFlow and deploy/serve the model with TensorFlow Lite.

In addition, we did some experimental work (heavier and inteded to be more robust) using word embedding with gensim models Word2Vec and content-based filtering concept.

For specific documentation of input, output, and process, kindly refer to documentation written in .ipynb.

## 3. Cloud Computing/Backend Description and List of Endpoints
There are some workloads for cloud computing team, such as database, backend, bucket, run Python from backend, API, and deploying backend on cloud.
This application using NodeJS as programming language and Express as its framework.
Here is a list of dependencies used and how to install them:
<ol>
  <li>Express: It is a fast and minimalist web application framework for Node.js. </li>
To install, run: npm install express <br/>
  
  <li>@google-cloud/storage: It is the official Google Cloud Storage client library.</li>
To install, run: npm install @google-cloud/storage <br/>
  
  <li>Multer: It is a middleware for handling multipart/form-data, primarily used for uploading files.</li>
To install, run: npm install multer <br/>

  <li>jsonwebtoken: It is an implementation of JSON Web Tokens (JWT) for Node.js.</li>
To install, run: npm install jsonwebtoken<br/>
  
  <li>express-session: It is a middleware that enables the use of sessions in Express.js.</li>
To install, run: npm install express-session<br/>
  
  <li>cors: It is a middleware that allows Cross-Origin Resource Sharing (CORS) in Express.js.</li>
To install, run: npm install cors<br/>
  </ol>
  
To install these dependencies, open your terminal or command prompt, navigate to the project directory, and run the respective npm install commands mentioned above.

Endpoints for applicants-side features:
<ol>
    <li>(POST) /signupApplicant </li>
        <ul>
            <li> Function: Sign up a new applicant.</li>
            <li> Implementation: Signup for applicant page.</li>
        </ul>
    <li>(POST) /loginApplicant </li>
        <ul>
            <li> Function: Log in an applicant.</li>
            <li> Implementation: Log in for applicant page.</li>
        </ul>
    <li>(POST) /setProfileApplicant </li>
        <ul>
            <li> Function: Set the profile data for an applicant.</li>
            <li> Implementation: Edit profile page.</li>
        </ul>
    <li>(POST) /uploadCV </li>
        <ul>
            <li> Function: Upload a CV (resume) for an applicant. </li>
            <li> Implementation: Upload CV page. </li>
        </ul>
    <li>(GET) /getProfile  </li>
        <ul>
            <li> Function: Get the profile data of an applicant.</li>
            <li> Implementation: About me Page.</li>
        </ul>
</ol>
      

Endpoints for companies-side features:
<ol>
    <li>(POST) /signupCompany </li>
        <ul>
            <li> Function: Sign up a new company.</li>
            <li> Implementation: Signup for company page.</li>
        </ul>
    <li>(POST) /loginCompany </li>
        <ul>
            <li> Function: Log in an company.</li>
            <li> Implementation: Log in for company page.</li>
        </ul>
    <li>(POST) /setProfileCompany </li>
        <ul>
            <li> Function: Set the profile data for a company.</li>
            <li> Implementation: Edit profile page.</li>
        </ul>
    <li>(GET) /getProfileCompany  </li>
        <ul>
            <li> Function: Get the profile data of a company.</li>
            <li> Implementation: About me Page.</li>
        </ul>
</ol>

Endpoints for chat applicants-side features:
<ol>
    <li>(POST) /api/chat/newchat </li>
          <ul>
              <li> Function: Create Room Chat if applicant accept the offer from company.</li>
              <li> Implementation: Accept button.</li>
          </ul>
    <li>(POST) /api/messages/sendfromapplicant </li>
        <ul>
            <li> Function: Send message from applicant to company.</li>
            <li> Implementation: Send message button.</li>
        </ul>
</ol>

Endpoints for chat companies-side features:
<ol>
    <li>(POST) /api/messages/sendfromcompany </li>
        <ul>
            <li> Function: Send message from company to applicant.</li>
            <li> Implementation: Send message button.</li>
        </ul>
</ol>

## 4. Mobile Development Description
Prototype of this mobile application was made with Figma. Link to our Figma (prototype) is as follow. <br/>
https://www.figma.com/file/KjWlvjAYf1CdEYnAkvxbS7/HireHub?type=design&node-id=0-1&t=diQxH05lD12A2Cdt-0

... (here!)

## 5. Replication and Duplication Steps
This section contains how to replicate (running the code given) and duplicate (remake/re-develop) this project.
### 5.1 Machine Learning
Assume that you didn't have Python or Jupyter Notebook installed in your device, then you should download and install Python and Jupyter Notebook first. After set up all the necessary options, follow this step below to replicate the project.
<ol>
  <li>Clone the project from GitHub (project contains code for Machine Learning, Cloud Computing, and Mobile Development)</li>
  <li>Download all the necessary library using this command on your Anaconda Prompt.</li>
  <ul>
    <li>pip install numpy</li>
    <li>pip install scikit-learn</li>
    <li>pip install tensorflow</li>
    <li>pip install keras</li>
    <li>pip install spacy</li>
    <li>pip install nltk</li>
    <li>pip install PyMuPDF</li>
    <li>pip install requests</li>
    <li>pip install tqdm</li>
    <li>(Optional: to run experimental work in RecommenderSystem.ipynb)pip install gensim</li>
  </ul>
  <li>Download the pre-built model with these commands.</li>
  <ul>
    <li>python -m spacy download en_core_web_sm</li>
    <li>python -m spacy download en_core_web_md</li>
  </ul>
</ol>
Up to those steps, you should be able to run the code in .ipynb. Further, if you want to duplicate the code from the beginning for further development, our workflow is as follow.
<ol>
  <li>Make ResumeParser.ipynb with each function in it.</li>
  <ul>
    <li>Using pre-built model</li>
    To use pre-built model, you can load the model with spacy.load() function, then pass the text you want to parse to the variable parameter. Result of recognition will be in ents, and for each entity in ents, there is label_ and text (label_ is recognized label, text is corresponding text recognized as label). <br/>
    For example, the code to print all texts associated with organization recognized by the model (en_core_web_md) from a text should look like this.
<pre>
model = spacy.load("en_core_web_md")
result = model(text)
for ent in result.ents:
  if ent.label_ == 'ORG':
    print(ent.text)
</pre>
    <li>Making your own pre-defined rule for model's pipe</li>
    <ol>
      <li>List all the keywords associated with your labels.</li>
      <li>Sort it based on alphabet.</li>
      <li>Split each word by its space.</li>
      <li>Make the pattern of rules for the jsonl's content.</li>
    </ol>
    Advantages: It can perfectly search for any occurence of certain listed keyword. <br/>
    Disadvantages: It can't search for any occurence besides the listed/pre-defined keywords. <br/>
    For more comprehensible details, you can refer to the Additional Python Works section in ResumeParser.ipynb.
    <li>Using pre-defined rule for model's pipe (JobZilla AI pre-defined, our project pre-defined, or your own rule)</li>
    To use pre-defined rule for model's pipe, you can add the "entity_ruler" pipe in a existing model, load the rules from disk, and do the inference as you have done in using pre-built model before. <br/>
    For example, the code to incorporate rules from hh_lang_pattern.jsonl file in en_core_web_md model should look like this.
<pre>
model = spacy.load("en_core_web_md")
ruler = model.add_pipe("entity_ruler", before="ner")
file = "hh_lang_pattern.jsonl"
ruler.from_disk(file)
result = model(text)
</pre>
    <li>Train our own model with SpaCy</li>
    To train our own model, follow this step.
    <ol>
      <li>Use the code to structure and create training data in ResumeParser.ipynb. For a quick recap, structure_training_data function get the text and list of keywords associated with a label, then annotate/mark the keywords occurence (substring) contained in certain text with that label. Then, create_training_set function will add data to a blank model document. Then, we will get the .spacy file for train and test data.</li>
      <li>Download base_config.cfg from link given in Additional Python Works section in ResumeParser.ipynb (make sure to choose the NER option).</li>
      <li>Configure the train and dev data with train and test data path.</li>
      <li>Initiate the training process and configure the training by running this code.</li>
<pre>
python -m spacy init fill-config base_config.cfg config.cfg
</pre>
      <li>Following steps before will give us config.cfg file, do train the model by running this code.</li>
<pre>
python -m spacy train config.cfg --output ./output
</pre>
      <li>The model will be located in output folder (to change this, change the ./output part to ./(folder_name) with (folder_name) is desired folder name. Use them for your entity recognition purposes.</li>
    </ol>
    <li>Using our own trained SpaCy model</li>
    Using our own trained SpaCy model is similar to using pre-built model with SpaCy. The difference only in the spacy.load() function's argument which should contain your model path.
    For example, if you want to use your own trained model in output/model-best directory, the code should look like this.
<pre>
model = spacy.load("output/model-best")
result = model(text)
for ent in result.ents:
  if ent.label_ == 'yourlabel':
    print(ent.text)
</pre>
  </ul>
  <li>Relocate core code of the .ipynb file to .py file (getting each entity, requests given PDF link and open the PDF, and getting argument from NodeJS backend).</li>
  <li>Make RecommenderSystem.ipynb with each function in it.</li>
  <li>Relocate core code of the .ipynb file to .py file (scoring/indexing each attribute and create a ready-to-use matrix).</li>
</ol>

### 5.2 Cloud Computing
1. Project configuration
   - Set the project name (Capstone-Project-Hirehub)
   - Connect the project to bangkit academy billing account
   - Set the organization as bangkit.academy
2. Bucket configuration for PDF storage 
   - Set the bucket name (bucket-pdf33)
   - Select the region as location type (asia-southeast2)
   - Uncheck the "enforce public access prevention"
   - Create the bucket
   - Set the bucket to public by giving permission to allUsers as viewer
4. MySQL configuration
   - Create new instance in SQL
   - Select MySQL
   - Set the ID and Password
   - Set the database 5.7 version 
   - Create the database in MySQL and create the table needed
   - Set the connection service so the database can be access from public internet
5. Service Account configuration
   - Create a service account as a credential to access the project
   - Add new key to acccess the service account
   - Download as JSON file and connect the key to code
6. Configure the App Engine
   - Turn on the App Engine service
   - Open editor and clone the code
   - Initialize project using gcloud init
   - Set the app.yaml into the right service (default and then backend)
   - Deploy with gcloud app deploy

### 5.3 Mobile Development

## 6. Future Works
There are some future works that team will take care of in the future to make sure that users have the best experience using the application, such as.
### 6.1 Machine Learning
#### 6.1.1 CV Parser
<ol>
  <li>Getting keyword from a CV</li>
  <li>Getting working experience from a CV</li>
  <li>Optimizing time and memory consumption of the parsing algorithm</li>
</ol>

#### 6.1.2 Recommender System
<ol>
  <li>Incorporating experimental work (word embedding + content-based filtering) to the system</li>
  <li>Simplifying and optimizing time and memory consumption of the preprocessing and recommendation algorithm</li>
</ol>

### 6.2 Cloud Computing
<ol>
  <li>Scaling the project further</li>
  <li>Separating the working server with ML algorithm server</li>
</ol>

### 6.3 Mobile Development
<ol>
  <li>Increasing UI/UX aspects</li>
  <li>Fitting responsiveness (for various device size) to the application</li>
</ol>
