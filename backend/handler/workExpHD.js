const { nanoid } = require('nanoid');
const workExperiences = require("../data/workExperiences");

const addExperienceHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { companyName, yearIn, yearOut, position, description} = request.payload;

    const id = nanoid(16);
    
    if (companyName === undefined){
        const response = h.response({
            status: 'fail',
            message: 'Failed, Company Name undefined', 
        });
        response.statusCode = 400;
        return response;
    } 

    const newWorkExperiences = {
        id, companyName, yearIn, yearOut, position, description
    }

    workExperiences.push(newWorkExperiences);

    const isSuccess = workExperiences.filter((workExperience) => workExperience.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                workExperienceId: id,
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
module.exports = addExperienceHandler;
