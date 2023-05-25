import nltk
from nltk.corpus import stopwords
from string import punctuation
import spacy
from spacy import displacy
from spacy.tokens import DocBin
import requests
import fitz
import re
import time
from tqdm import tqdm

model_md = spacy.load("en_core_web_md")
model_sm = spacy.load("en_core_web_sm")
ruler = model_sm.add_pipe("entity_ruler", before="ner")

def take_email(text):
    text = " ".join(text.split("\n"))
    emailpat = r'[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}'
    try :
        email = re.findall(emailpat, text)[0]
    except:
        email = ''
    return email

def take_mp(text):
    text = " ".join(text.split("\n"))
    mppat = r'(?![0-9]{5}\s[0-9]{5})\+?\d{1,3}[-.\s]?\(?\d{2,3}\)?[-.\s]?\d{2,4}[-.\s]?\d{2,4}[-.\s]?\d{2,4}'
    try :
        mp = re.findall(mppat, text)[0]
    except:
        mp = ''
    return mp

def take_name(text):
    text = re.sub(r'\s+\.', '.', text)
    text_copy = text
    find = False
    for row in text_copy.split("\n"):
        if find == True:
            person = re.sub(r':\s*', '', row)
            person = re.sub(r'^\s*', "", person)
            person = re.sub(r'\s*$', "", person)
            return person
        match = re.search(r'\b(?<!\s)(?!of\s+company)(?:fullnames|fullname|names|name)(?=\s*)(?!\s*[a-z0-9_])', row.lower())
        if match:
            if ":" in row.lower():
                person = re.sub(r'(?i)name\s*:\s*', '', row)
                if person == "":
                    find = True
                else:
                    person = re.sub(r'^\s*', "", person)
                    person = re.sub(r'\s*$', "", person)
                    return person
            if "-" in row.lower():
                person = re.sub(r'(?i)name\s*-\s*', '', row)
                person = re.sub(r'^\s*', "", person)
                person = re.sub(r'\s*$', "", person)
                return person
            else:
                rem = str(row)
                rem = re.sub(r'(?i)(names|fullnames|name|fullname)', '', rem)
                if re.search(r'\s*', rem):
                    find = True
                else:
                    return rem
        
    custom_stopword = ["educational", "objective", "project", "reference",  "experience",  "address"]
    init_len = len(text)
    for sub_str in custom_stopword:
        if sub_str in text.lower():
            text_drop = text[:text.lower().index(sub_str)]
            if len(text_drop) >= 0.01 * init_len:
                text = text_drop
    
    custom_row = ["company", "mother", "father", "blessed", "id", "training"]
    filter_row = []
    for row in text.split("\n"):
        if not any(drop in row.lower() for drop in custom_row):
            filter_row.append(row)
    text = "\n".join(filter_row)
    text = ' '.join([word for word in text.split() if word.lower() not in (stopwords.words('english'))])
    text = " ".join(text.split("\n")).lower()
    
    custom_drop = ("civil", "skill", "permanent", "mail", "email", "add", "academic", "branch", "present", "mechanic", 
                   "engineer", "apply", "programming", "examination", "learning", "university", "employment", "date", 
                   "birth", "place", "succeed", "model", "name", "personal", "profile", "page", "secure", "culture", 
                   "offer", "company", "work", "successfully", "growth", "oriented", "field", "infrastructur", 
                   "qualification", "electronic", "entry", "seeking", "position", "organization", "vitae", "curri", 
                   "cirri", "curi", "instagram", "email", "current", "plot", "resume", "contact", "mobile", "phone", 
                   "no", "nationality", "id", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "block", "correspon", 
                   "passport", "career", "stren")
    text = ' '.join([word for word in text.split() if not word.lower().startswith(custom_drop) and not word.lower().endswith(custom_drop)])
        
    custom_punc = [",", "/", "  ", "-", ":", "(", ")"]
    for punc in custom_punc:
        text = text.replace(punc, "")
    
    text = " ".join(st[0].upper()+st[1:] for st in text.split(" ") if st != "")
    resultnlp = model_md(text)
    k = 0
    for ent in resultnlp.ents:
        if ent.label_ == 'PERSON' and k == 0:
            if len(str(ent.text).split(" ")) > 7:
                person = " ".join(str(ent.text).split(" ")[:5])
            else:
                person = str(ent.text)
            k += 1
    if k == 0:
        resultnlp = model_sm(text)
        k = 0
        for ent in resultnlp.ents:
            if ent.label_ == 'PERSON' and k == 0:
                if len(str(ent.text).split(" ")) > 7:
                    person = " ".join(str(ent.text).split(" ")[:5])
                else:
                    person = str(ent.text)
                k += 1
    if k == 0:
        for ent in resultnlp.ents:
            if ent.label_ == 'ORG' and k == 0:
                person = str(ent.text)
                k += 1
        if k == 0:
            if len(text.split(" ")) < 5:
                person = text
            else:
                person = ""
            
    return person

def take_skills(text):
    text = " ".join(text.split("\n"))
    skills = "jz_skill_patterns.jsonl"
    ruler.from_disk(skills)
    resultnlp = model_sm(text)
    skillsList = []
    for ent in resultnlp.ents:
        if ent.label_ == 'SKILL':
            trait = str(ent.text)
            skillsList.append(trait.upper())
            skillsList = list(set(skillsList))
    return skillsList

def take_lang(text):
    doc = fitz.open(pdfpath)
    text = ""
    for page in doc:
        text = text + str(page.get_text())
    text = re.sub(r'[^\x00-\x7F]', '', text)
    text = " ".join(text.split("\n"))
    custom_drop = ["purpose"]
    for drop in custom_drop:
        text = text.lower().replace(drop, "")
    langs = "hh_lang_pattern.jsonl"
    ruler.from_disk(langs)
    resultnlp = model_sm(text)
    langList = []
    for ent in resultnlp.ents:
        if ent.label_ == 'LANG':
            lan = str(ent.text)
            langList.append(lan.upper())
            langList = list(set(langList))
    return langList

def take_edu(text):
    text = re.sub(r':', ':\n', text)
    init_len = len(text)
    
    custom_mark = ["education", "academic", "scholastic", "institut"]
    for sub_str in custom_mark:
        for row in text.lower().split("\n"):
            if sub_str in row.lower() and len(row.split(" ")) <= 5:
                text = text[text.lower().index(sub_str):]
                break
    
    custom_stopword = ["strength", "area", "expertise", "training", "experience", "project", "reference", "contact", "work", "certificat", "job", "personal", "profile"]
    for sub_str in custom_stopword:
        for row in text.lower().split("\n"):
            if sub_str in row.lower() and len(row.split(" ")) <= 5 and sub_str in text.lower():
                text_stop = text[:text.lower().index(sub_str)]
                if len(text_stop) >= 0.05 * init_len:
                    text = text_stop
    
    custom_punc = [",", "/", "  ", "-", ":", "(", ")"]
    for punc in custom_punc:
        text = text.replace(punc, "")
    
    text = " ".join(st[0].upper()+st[1:] for st in text.split(" ") if st != "")
    edupat = r'\b\s*\w+(?:\s+\w+)*\s*(?i:universit(?:y|é|a(?:s|des)?|ies)|polytechnic|institute|college|academy)\w?\s*\b'
    pos_list = list(re.findall(edupat, text))
    
    filt_list = []
    custom_drop = ["education", "scholastic", "institution", "qualification", "from", 'civil', 'bcom', 'mcom', 'btech', 'mtech', 'phd', 'mba','graduate', 'postgraduate', 'master', 'ssc', 'hsc', 'cbse', 'icse', "engineering", "from", "mechanical", "class"]
    for edu in pos_list:
        for drop in custom_drop:
            if drop in edu.lower():
                edu = edu[edu.lower().index(drop):]
            edu = ' '.join(word for word in edu.split() if not word.lower() == drop.lower())
            filt_list.append(edu)
    
    edu_filt = ""
    for pos in filt_list:
        if len(pos.split(" ")) > 1 and len(pos.split(" ")) < 7:
            edu_filt = pos
    return edu_filt

def take_sum(text):
    text = re.sub(r':', ':\n', text)
    init_len = len(text)
       
    found = False
    custom_stopword = ["strength", "expertise", "training", "scholastics", "qualification", "experience", "education", "project", "reference", "contact", "academic", "work", "certificat", "history", "job", "personal", "profile"]
    custom_mark = ["summary", "about me", "introduction", "synopsis", "abstract", "highlight", "overview", "objective", "purpose"]
    for sub_str in custom_mark:
        for row in text.lower().split("\n"):
            if sub_str in row.lower() and len(row.split(" ")) <= 5 and not any(sw in row.lower() for sw in custom_stopword):
                text = text[text.lower().index(sub_str):]
                found = True
            if found == True:
                break
        if found == True:
            break
    
    for sub_str in custom_stopword:
        for row in text.lower().split("\n"):
            if sub_str in row.lower() and len(row.split(" ")) <= 5 and sub_str in text.lower():
                tex = text[:text.lower().index(sub_str)]
                if len(tex) >= 0.01 * init_len:
                    text = tex
    
    custom_drop = ["purpose", "objective", "overview", "highlight", "abstract", "synopsis", "introduction", "about me", "summary"]
    for sub_str in custom_drop:
        for row in text.lower().split("\n"):
            if sub_str in row.lower() and len(row.split(" ")) <= 5 and sub_str in text.lower():
                tex = text[:text.lower().index(sub_str)]
                if len(tex) >= 0.01 * init_len:
                    text = tex
    
    filter_row = []
    for row in text.split("\n"):
        if not any(drop in row.lower() for drop in custom_drop):
            filter_row.append(row)
    
    summary = " ".join(filter_row)
    return summary

def take_degree(text):
    text = " ".join(text.split("\n"))
    nlp = spacy.load("output/model-best")
    resultnlp = nlp(text)
    degreeD = ["diploma", "amd", "ama", "str", "ap"]
    degree1 = ["bachelor", "btech",  "sarjana", 'ba', 'bcom', 'be', 'btech', 'bsc', 'bba', 'skom', 'se', 'ssi', 'ssn', 'sikom', 'sip', 'sipol', 'sh', 'spd', 'sked', 'skeb', 'sgz', 'shum', 'spsi']
    degree2 = ["mba", 'master', 'magister', 'msc', 'mm', 'ma', 'msi', 'mtech', 'mkom', 'mcs', 'mpsi', 'mpd', 'drs', 'mmgt']
    degree3 = ['prof', 'phd', 'thd', 'dth', 'edd']
    degreeK = ['apt', 'dr', 'ir', 'mr']
    degreeDList = []
    degree1List = []
    degree2List = []
    degree3List = []
    degreeKList = []
    degreeList = []
    for ent in resultnlp.ents:
        if ent.label_ == 'DEGREE':
            degree = str(ent.text)
        if degree in punctuation:
            continue
        degree_clean = re.sub(r'[^\w\s]', '', degree)
        if degree_clean.lower() in degreeD:
            degreeDList.append(degree.upper())
        elif degree_clean.lower() in degree1:
            degree1List.append(degree.upper())
        elif degree_clean.lower() in degree2:
            degree2List.append(degree.upper())
        elif degree_clean.lower() in degree3:
            degree3List.append(degree.upper())
        elif degree_clean.lower() in degreeK:
            degreeKList.append(degree.upper())
        else:
            degreeList.append(degree)
        degreeList = list(set(degreeList))
        degreeDList = list(set(degreeDList))
        degree1List = list(set(degree1List))
        degree2List = list(set(degree2List))
        degree3List = list(set(degree3List))
        degreeKList = list(set(degreeKList))
    return degreeDList, degree1List, degree2List, degree3List, degreeKList, degreeList

def take_country(text):
    text = " ".join(text.split("\n"))
    loc = "hh_country_pattern.jsonl"
    ruler.from_disk(loc)
    resultnlp = model_sm(text)
    loc = ''
    for ent in resultnlp.ents:
        if ent.label_ == 'COUNTRY':
            country = str(ent.text)
            loc = country
            break
    return loc

def cvparsing(pdflink):
    response = requests.get(pdflink)
    content = response.content

    doc = fitz.open(stream=content, filetype='pdf')

    text = ""
    for page in doc:
        text = text + str(page.get_text())
    text = re.sub(r'[^\x00-\x7F]', '', text)

    profDic["PERSON"] = take_name(text)
    profDic["EMAIL"] = take_email(text)
    profDic["MOBILE"] = take_mp(text)
    profDic["SUM"] = take_sum(text)
    profDic["SKILL"] = take_skills(text)
    profDic["LANG"] = take_lang(text)    
    profDic["EDU"] = take_edu(text)
    profDic['DEGREE'] = take_degree(text)
    profDic['LOC'] = take_country(text)

    return profDic