# HireHub
HireHub was inspired by the fact that companies still rely on their HR to filter thousands of job applicants’ CVs. This process is time-consuming and labor-intensive. On the other hand, applicants usually have to make their online in-app profile from scratch which leads to inconsistency between their CV and online profile. Therefore, we offer this application to tackle the problem with recommenders for the company and CV text processing to generate online profiles automatically. With this application, HR will spend less time dealing with irrelevant applicants and the applicants will be helped in making their online presence in-app.

HireHub is a talent search and recruitment platform that connects job seekers with potential employers. This application allows job seekers to create an online profile as simply as uploading their resume and wait for the company to offer them any related job. Employers can search for the candidate and our recommenders system in-app will help them to find the best candidate for specified criteria. This application also offers messaging feature between employers and candidates to enhance the recruitment experience. HireHub aims to surpass beyond nowadays recruitment process with some features, such as CV processing to create online profiles automatically and the recommenders system to help the company with filtering suitable candidates for specified positions.

Hirehub has two user-level, the applicants and the companies. Firstly, the applicants and companies should register and log into the application. Then, each user level has its own functionalities. Applicants can drop their CVs/resumes in our application for companies’ HR to evaluate. In our competitor apps, when applicants want to make their profile, they should do it from scratch and manually input all of their data. Thus, we introduce the second usage of applicants' CVs which is to parse their resume and get the information needed to make their profile, such as name, location (country), mobile phone number, email, skills, languages, education institution, education degree, and their professional summary. However, we still have the feature for manual editing/input since some attributes might not or never be present in a CV/resume, such as age/birth date/birth year and salary expectation or our model badly parse the resume. After applicants drop their resumes and make online profiles, they just need to wait until company offers come and they have the right to accept or reject them. They can also chat with the company during the recruitment process.

On the other hand, company user-level can make their profile online manually. They can also search for applicants/talents with simple steps, such as setting the filter (filtering age/skills/other) and then searching. Then, our application will recommend (with the recommender system that we develop) some applicants in a list from the most relevant to the filter all the way down to the most irrelevant to the filter. HR can then see their online profile and CV, then can offer the applicants a job. HR needs to wait for applicants' responses and if applicants accept the offer for recruitment, the recruitment process will be facilitated via chat in our application. Then, the company will have the right to cancel the recruitment process (since the applicants are no longer relevant, mismatch in the interview/technical test, etc.) or accept the applicants to their companies. If the applicants are accepted by the company, then they will disappear from the search list and the running recruitment process will be terminated.

### 1. Technical Stack
This application built using technical stack explained below.
<ol>
  <li> Machine Learning: 
    <ul>
      <li> Main Language : Python </li>
      <li> IDE : Jupyter Notebook & Visual Studio Code </li>
      <li> Library : NLTK, SpaCy, string, requests, fitz (PyMuPDF), re, time, tqdm, sys, json, sklearn, tensorflow, keras, pathlib, warnings </li>
      <li> Pre-built model: en_core_web_sm & en_core_web_md </li>
      <li> New findings: model to detect academic degree in /script/output/model-best, three entity content-based filtering (in RecommenderSystem.ipynb, Experimental Work section), and two JSONL files to detect country/languages in a CV </li>
    </ul>
  </li>
  <li> Mobile Development: 
    <ul>
      <li> Main Scripting/Programming Language: Kotlin & XML </li>
      <li> IDE: Android Studio </li>
      <li> Library: Retrofit </li>
    </ul>
  </li>
  <li> Cloud Computing: 
    <ul>
      <li> Main Programming Language: NodeJS & SQL (MySQL) </li>
      <li> IDE: Visual Studio Code </li>
      <li> Framework: ExpressJS </li>
      <li> Services: Google Cloud Services </li>
    </ul>
  </li>
</ol>

### 2. Data Usage
This application using data from various source. 
For Machine Learning training purposes, we use data from.
<ol>
  <li>Resume Dataset - [URL: https://www.kaggle.com/datasets/aishikai/resume-dataset]</li>
  Details <br/>
  Owner : Aishik Rakshit <br/>
  Content : Collection of Curriculum Vitae files <br/>
  Formats : PDF and Microsoft word <br/>
  Language : Mostly English <br/>
  Total files : 3264 (PDF File), 2296 (Word File) <br/>
  Format used : Only PDF (All of the files) <br/>
  <br/>
  Additional Information <br/>
  -Most of the CVs data are from Indians <br/>
  -Data collected from various random websites <br/>
  -Kaggle Usability Rating 2.50 <br/>
  <br/>
  <li>Resume Data with Annotations - [URL: https://www.kaggle.com/datasets/extremelysunnyyk/resume-data-with-annotations]</li>
  Details <br/>
  Owner : Yong Kang Chia <br/>
  Content : CV files in PDF and Microsoft Word format, Poster files, and Training Data files <br/>
  Language : English <br/>
  Total files : 8 (PDF File), 2 (Word File), 1 (pkl File), 2 (txt file) <br/>
  Format used : Only PDF (5 Files) <br/>
  <br/>
  Additional Information <br/>
  -Kaggle Usability Rating 3.13 <br/>
  <br/>
  <li>Team Member CVs'</li>
  Details <br/>
  Owner: Reynard Matthew Yaputra, Nadya Angelia, Andelle Gianzra Basae
</ol>


### 3. Backend
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

### 4. List Of All Endpoint
For Applicants:
<ol>
    <li>(POST) /signupApplicant </li>
        <ul>
            <li> Function         : Sign up a new applicant.</li>
            <li> Implementation   : Signup for applicant page.</li>
        </ul>
    <li>(POST) /loginApplicant </li>
        <ul>
            <li> Function         : Log in an applicant.</li>
            <li> Implementation   : Log in for applicant page.</li>
        </ul>
    <li>(POST) /setProfileApplicant </li>
        <ul>
            <li> Function         : Set the profile data for an applicant.</li>
            <li> Implementation   : Edit profile page.</li>
        </ul>
    <li>(GET) /getProfile  </li>
        <ul>
            <li> Function         : Get the profile data of an applicant.</li>
            <li> Implementation   : About me Page.</li>
        </ul>
</ol>
      

For Company:
<ol>
    <li>(POST) /signupCompany </li>
        <ul>
            <li> Function         : Sign up a new company.</li>
            <li> Implementation   : Signup for company page.</li>
        </ul>
    <li>(POST) /loginCompany </li>
        <ul>
            <li> Function         : Log in an company.</li>
            <li> Implementation   : Log in for company page.</li>
        </ul>
    <li>(POST) /setProfileCompany </li>
        <ul>
            <li> Function         : Set the profile data for a company.</li>
            <li> Implementation   : Edit profile page.</li>
        </ul>
    <li>(GET) /getProfileCompany  </li>
        <ul>
            <li> Function         : Get the profile data of a company.</li>
            <li> Implementation   : About me Page.</li>
        </ul>
</ol>

Other Endpoints:
<ol>
    <li>(POST) /uploadCV </li>
        <ul>
            <li> Function         : Upload a CV (resume) for an applicant. </li>
            <li> Implementation   : Upload CV page. </li>
        </ul>
</ol>
  
