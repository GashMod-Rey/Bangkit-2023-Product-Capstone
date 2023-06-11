import json
import numpy as np
import sys
from datetime import datetime

def score_skill(skill, skillFilter):
    skill = [x.upper() for x in skill]
    skillFilter = [x.upper() for x in skillFilter]

    skill = set(skill).intersection(skillFilter)

    intersection = len(list(set(skill).intersection(skillFilter)))
    union = (len(set(skill)) + len(set(skillFilter))) - intersection

    jaccard_index = float(intersection) / union
    return round(jaccard_index, 2)

def score_lang(lang, langFilter):
    lang = [x.upper() for x in lang]
    langFilter = [x.upper() for x in langFilter]

    lang = set(lang).intersection(langFilter)

    intersection = len(list(set(lang).intersection(langFilter)))
    union = (len(set(lang)) + len(set(langFilter))) - intersection

    jaccard_index = float(intersection) / union
    return round(jaccard_index, 2)

def score_age(age, ageFilter, tolerance):
    if age >= ageFilter[0] and age <= ageFilter[1]:
        index = 1
    elif age >= ageFilter[1] and age <= ageFilter[1] + tolerance:
        index = round((tolerance + 1 - age + ageFilter[1])/(tolerance + 1), 2)
    elif age <= ageFilter[0] and age >= ageFilter[0] - tolerance:
        index = round((tolerance + 1 - ageFilter[0] + age)/(tolerance + 1), 2)
    else:
        index = 0

    return index

def score_salary(salary, salaryFilter, tol):
    if salary >= salaryFilter[0] and salary <= salaryFilter[1]:
        index = 1
    elif salary >= salaryFilter[1]:
        index = round((tol + 1 + salaryFilter[1] - salary)/(tol + 1), 2)
        if index < 0:
            index = 0
    elif salary <= salaryFilter[0]:
        index = 1

    return index

def scoring(data_appl, data_filter):
    x = []

    for _ in range(len(data_filter)):
        x.append([])
        
    i = 0
    for filt in data_filter:
        for person in data_appl:
            sk = score_skill(person["SKILL"], filt["skillFilter"])
            lg = score_lang(person["LANG"], filt["langFilter"])
            ag = score_age(person["AGE"], filt["ageFilter"], filt["tolerance"])
            sl = score_salary(person["SALARY"], filt["salaryFilter"], filt["tol"])
            x[i].append([sk, lg, ag, sl])
        i = i+1

    x = np.array(x).reshape(-1, 4)
    x = x.tolist()
    x = json.dumps(x)
    print(x)

json_data_appl = sys.argv[1]
json_data_filter = sys.argv[2]
data_appl_raw = json.loads(json_data_appl)
data_filter = json.loads(json_data_filter)
data_appl = []

for appl in data_appl_raw:
    temp = {}
    
    if appl["Skills"] != None:
        temp["SKILL"] = appl["Skills"].split(", ")
    else:
        temp["SKILL"] = [""]

    if appl["Language"] != None:
        temp["LANG"] = appl["Language"].split(", ")
    else:
        temp["LANG"] = [""]

    if appl["YearOfBirth"] != None:
        current_date = datetime.utcnow()
        birth_date = datetime.strptime(appl["YearOfBirth"].split("T")[0], "%Y-%m-%d")
        age = current_date.year - birth_date.year
        if current_date.month < birth_date.month or (current_date.month == birth_date.month and current_date.day < birth_date.day):
            age -= 1
        temp["AGE"] = age
    else:
        temp["AGE"] = sys.maxsize

    if appl["SalaryMinimum"] != None:
        temp["SALARY"] = appl["SalaryMinimum"]
    else:
        temp["SALARY"] = sys.maxsize
    data_appl.append(temp)

scoring(data_appl, data_filter)