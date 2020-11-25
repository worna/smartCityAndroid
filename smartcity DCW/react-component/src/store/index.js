import {createStore, combineReducers} from "redux";
const liste = [
    {
        id: 1,
        name: "Mouvements",
        manager: "Vicky",
        phoneNumber: "0496818836",
        email: "etu41165@henallux.be"
    },
    {
        id: 2,
        name: "Jims",
        manager: "Arnaud",
        phoneNumber: "0474025605",
        email: "etu40153@henallux.be"
    },
    {
        id: 3,
        name: "Basic Fit",
        manager: "Donny",
        phoneNumber: "0123456789",
        email: "etu1234@henallux.be"
    },
    {
        id: 4,
        name: "CrossFit",
        manager: "Emma",
        phoneNumber: "0987654321",
        email: "etu2345@henallux.be"
    },
    {
        id: 5,
        name: "Enjoy",
        manager: "Georgy",
        phoneNumber: "753196248",
        email: "etu456@henallux.be"
    }
];

const sportHallReducer = (state = {listeSportHalls: liste}, action) => {
    const listeSportHalls = state.listeSportHalls;
    const newArray = [...listeSportHalls];
    switch (action.type) {
        case "updateSportHall":
            const updatedSportHall = action.payload.newSportHall;
            const index = newArray.findIndex(h => h.id === updatedSportHall.id);
            newArray[index] = updatedSportHall;
            return {
                listeSportHalls: newArray
            }
        case "addSportHall":
            const newSportHall = action.payload.newSportHall;
            newSportHall.id = newArray.length + 1;
            newArray.push(newSportHall);
            return {
                listeSportHalls: newArray
            }

        default:
            return state;
    }
};

const store = createStore(combineReducers({sportHalls: sportHallReducer}));

export default store;