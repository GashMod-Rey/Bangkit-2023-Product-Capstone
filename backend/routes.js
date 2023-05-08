// const { 
//     addBookHandler, 
//     getAllBooksHandler, 
//     getBookByIdHandler, 
//     editBookByIdHandler, 
//     deleteBookByIdHandler } = require("./handler");

const { addProfileHandler } = require("./handler/profileHD");
const { addAchievementHandler } = require("./handler/achievementHD");
const { addEducationHistoryHandler } = require("./handler/edHistoryHD");
const { addOrgHistoryHandler } = require("./handler/orgHistorieHD");
const { addSkillHandler } = require("./handler/skillHD");
const { addExperienceHandler } = require("./handler/workExpHD");

const routes = [
    {
        method: 'POST',
        path: '/profiles',
        handler: addProfileHandler,
    },
    {
        method: 'POST',
        path: '/achievements',
        handler: addAchievementHandler,
    },
    {
        method: 'POST',
        path: '/educationHistories',
        handler: addEducationHistoryHandler,
    },
    {
        method: 'POST',
        path: '/orgHistories',
        handler: addOrgHistoryHandler,
    },
    {
        method: 'POST',
        path: '/skills',
        handler: addSkillHandler,
    },
    {
        method: 'POST',
        path: '/workExperiences',
        handler: addExperienceHandler,
    },

    // {
    //     method: 'GET',
    //     path: '/books',
    //     handler: getAllBooksHandler,
    // },
    // {
    //     method: 'GET',
    //     path: '/books/{id}',
    //     handler: getBookByIdHandler,
    // },
    // {
    //     method: 'PUT',
    //     path: '/books/{id}',
    //     handler: editBookByIdHandler,
    // },
    // {
    //     method: 'DELETE',
    //     path: '/books/{id}',
    //     handler: deleteBookByIdHandler,
    //   },
  ];
   
module.exports = routes;