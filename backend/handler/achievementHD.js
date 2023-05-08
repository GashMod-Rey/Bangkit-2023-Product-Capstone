const { nanoid } = require('nanoid');
const achievements = require("../data/achievements");

const addAchievementHandler = (request, h) => {
    // const { name, year, author, summary, publisher, pageCount, readPage, reading} = request.payload;
    const { achievementName, description} = request.payload;

    const id = nanoid(16);
    
    if (achievementName === undefined){
        const response = h.response({
            status: 'fail',
            message: 'Failed, Achievement Name undefined', 
        });
        response.statusCode = 400;
        return response;
    } 

    const newAchievements = {
        id, achievementName, description
    }

    achievements.push(newAchievements);

    const isSuccess = achievements.filter((achievement) => achievement.id === id).length > 0;
 
    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Success',
            data: {
                achievementId: id,
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
module.exports = addAchievementHandler;