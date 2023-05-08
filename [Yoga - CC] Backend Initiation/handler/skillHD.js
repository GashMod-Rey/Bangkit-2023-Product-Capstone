const { nanoid } = require('nanoid');
const skills = require("../data/skills");

const addSkillHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { softSkill, hardSkill} = request.payload;

    const id = nanoid(16);
    
    // if (softSkill === undefined){
    //     const response = h.response({
    //         status: 'fail',
    //         message: 'Failed, name undefined', 
    //     });
    //     response.statusCode = 400;
    //     return response;
    // } 

    const newSkills = {
        id, softSkill, hardSkill
    }

    skills.push(newSkills);

    const isSuccess = skills.filter((skill) => skill.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                skillId: id,
            },
        });
        response.statusCode = 201;
        return response;
    }
    if(!isSuccess){
        const response = h.response({
            status: "fail",
            message: "Failed",
        });
        response.statusCode = 500;
        return response;
    }
}
module.exports = addSkillHandler;
