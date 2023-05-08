const { nanoid } = require('nanoid');
const edHistories = require('../data/educationHistories');

const addEducationHistory = (request, h) => { 

  const { school, major, position, graduationYear } = request.payload;

  const id = nanoid(16);

  if (school === undefined){
    const response = h.response({
        status: 'fail',
        message: 'Failed, school undefined', 
    });
    response.statusCode = 400;
    return response;
  } 

  const neweducationHistories = {
      id, school, major, position, graduationYear
  }

  edHistories.push(neweducationHistories);

  const isSuccess = edHistories.filter((edHistory) => edHistory.id === id).length > 0;

  if (isSuccess) {
      const response = h.response({
          status: 'success',
          message: 'Success',
          data: {
              applicantId: id,
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
};
module.exports = addEducationHistory;