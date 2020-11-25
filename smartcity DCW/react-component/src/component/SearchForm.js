import React from 'react';
import SearchBar from './SearchBar';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';

class SearchForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            sportHalls: this.props.sportHalls,
            sportHallsToShow: this.props.sportHalls,
            inputName: "",
            inputManager: "",
            inputPhoneNumber: "",
            inputEmail: "",
        }
    }

    componentDidUpdate(prevProps) {
        if(this.props !== prevProps){
            this.setState({
                sportHalls: this.props.sportHalls,
                sportHallsToShow: this.props.sportHalls
            });
        }
    }

    addSportHall(event){
        event.preventDefault();
        const newSportHall = {
            name: this.state.inputName,
            manager: this.state.inputManager,
            phoneNumber: this.state.inputPhoneNumber,
            email: this.state.inputEmail
        }
        this.props.addSportHall(newSportHall);
    }

    changeValuesToShow(string){
        const sportHallsToShow = this.state.sportHalls;
        const afterFiltering = sportHallsToShow.filter(h => {
            return h.name.includes(string);
        });
        this.setState({sportHallsToShow: afterFiltering});
    }

    render() {
        return (
            <div>
                <SearchBar callback={(searchValue) => this.changeValuesToShow(searchValue)}/>
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Manager</th>
                            <th>Phone number</th>
                            <th>Email</th>
                            <th>Voir plus d'info</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.sportHallsToShow.map((h, index) => {
                            return (
                                <tr key={index}>
                                    <td>{h.name}</td>
                                    <td>{h.manager}</td>
                                    <td>{h.phoneNumber}</td>
                                    <td>{h.email}</td>
                                    <td>
                                        <Link to={`/sportHall/${h.id}`}>Lien</Link>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <form>
                    <label>Name: </label>
                    <input type="text"
                           onChange={(event) => {
                               this.setState({inputName: event.target.value});
                           }}/>
                    <label >Manager: </label>
                    <input type="text"
                           onChange={(event) => {
                               this.setState({inputManager: event.target.value});
                           }}/>
                    <label >Phone number: </label>
                    <input type="text"
                           onChange={(event) => {
                               this.setState({inputPhoneNumber: event.target.value});
                           }}/>
                    <label >Email: </label>
                    <input type="email"
                           onChange={(event) => {
                               this.setState({inputEmail: event.target.value});
                           }}/>
                    <button onClick={(event) => this.addSportHall(event)}>Ajouter</button>
                </form>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        sportHalls : state.sportHalls.listeSportHalls
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        addSportHall: (sportHallObjet) => {
            dispatch({type: "addSportHall", payload:{newSportHall: sportHallObjet}});
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(SearchForm);