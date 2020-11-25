import React from 'react';
import {connect} from 'react-redux';
import {Redirect} from 'react-router-dom';

class SportHall extends React.Component{

    constructor(props) {
        super(props);
        const sportHalls = this.props.sportHalls;
        const id = parseInt(this.props.match.params.id);
        const [sportHall] = sportHalls.filter(h => h.id === id);
        this.state = {
            id,
            name: sportHall.name,
            manager: sportHall.manager,
            phoneNumber: sportHall.phoneNumber,
            email: sportHall.email,
            redirect: false
        }
    }

    sauvegarder(event){
        event.preventDefault();
        this.props.updateSportHall({
            id: this.state.id,
            name: this.state.name,
            manager: this.state.manager,
            phoneNumber: this.state.phoneNumber,
            email: this.state.email
        });
        this.setState({redirect: true});
    }

    render(){
        return(
            <form>
                <label>Name: </label>
                <input type="text"
                       value={this.state.name}
                       onChange={(e) => this.setState({name: e.target.value})}
                />
                <label>Manager: </label>
                <input type="text"
                       value={this.state.manager}
                       onChange={(e) => this.setState({manager: e.target.value})}
                />
                <label>Phone number: </label>
                <input type="text"
                       value={this.state.phoneNumber}
                       onChange={(e) => this.setState({phoneNumber: e.target.value})}
                />
                <label>Email: </label>
                <input type="email"
                       value={this.state.email}
                       onChange={(e) => this.setState({email: e.target.value})}
                />
                <button
                    onClick={
                        (e) => this.sauvegarder(e)
                    }>
                    Sauvegarder
                </button>
                {this.state.redirect && <Redirect to={"/"}/>}
            </form>
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
        updateSportHall: (sportHallObjet) => {
            dispatch({type: "updateSportHall", payload:{newSportHall: sportHallObjet}});
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(SportHall);