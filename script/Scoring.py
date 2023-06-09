import numpy as np

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
    return x