# HireHub

This is a talent-searching application, where there are two user-level, the applicants and the companies. Firstly, the applicants and companies should register and log into the application. Then, each user level has its own functionalities. Applicants can drop their CVs/resumes in our application for companies’ HR to evaluate. In our competitor apps, when applicants want to make their profile, they should do it from scratch and manually input all of their data. Thus, we introduce the second usage of applicants' CVs which is to parse their resume and get the information needed to make their profile, such as name, location (country), mobile phone number, email, skills, languages, education institution, education degree, and their professional summary. However, we still have the feature for manual editing/input since some attributes might not or never be present in a CV/resume, such as age/birth date/birth year and salary expectation or our model badly parse the resume. After applicants drop their resumes and make online profiles, they just need to wait until company offers come and they have the right to accept or reject them. They can also chat with the company during the recruitment process.

On the other hand, company user-level can make their profile online manually. They can also search for applicants/talents with simple steps, such as setting the filter (filtering age/skills/other) and then searching. Then, our application will recommend (with the recommender system that we develop) some applicants in a list from the most relevant to the filter all the way down to the most irrelevant to the filter. HR can then see their online profile and CV, then can offer the applicants a job. HR needs to wait for applicants' responses and if applicants accept the offer for recruitment, the recruitment process will be facilitated via chat in our application. Then, the company will have the right to cancel the recruitment process (since the applicants are no longer relevant, mismatch in the interview/technical test, etc.) or accept the applicants to their companies. If the applicants are accepted by the company, then they will disappear from the search list and the running recruitment process will be terminated.

### 1. Data Usage
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
