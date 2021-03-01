import React, { Component } from 'react';
import ls from 'local-storage';
import Helmet from 'react-helmet';
import axios from 'axios';
import Nav from './Nav';
import UserNav from './UserNav';
import Login from './Login';
import Signin from './Signin';
import Content from './Content';
import Profile from './Profile';

class NavigationPannel extends Component {

  constructor(props){
    super(props);
    this.state = {requestedProfile : this.props.user.login, userProfile : this.props.user.login, page: "main", useless: 0};
    this.viewPage = this.viewPage.bind(this);
    this.requestProfile = this.requestProfile.bind(this);
    this.viewProfile = this.viewProfile.bind(this);
    
    this.follow = this.follow.bind(this);
   }

  requestProfile(user) {
    this.state.requestedProfile = user;
    this.setState({});
  }

  viewProfile(user) {
    this.state.userProfile = user;
    this.setState({});
    //this.setState({userProfile: {user}});
  }

  viewPage(p) { 
    if (p == "profile") { // y'a un pb avec ça
      //this.requestProfile(this.props.user.login);
      //this.viewProfile(this.props.user.login);
      }
  
    this.state.page = p;
    this.setState({});
  }

  follow(user_key, friend) {
    axios.get("http://localhost:8080/project4/follow?key=" + user_key + "&friend=" + friend).then(rep => {}).catch(error => {console.log(error)});
    
    //TROUVER MOYEN D'UPDATE ICI
  }

  unfollow(user_key, friend) {
    axios.get("http://localhost:8080/project4/unfollow?key=" + user_key + "&friend=" + friend).then(rep => {}).catch(error => {console.log(error)});
    
    //TROUVER MOYEN D'UPDATE ICI
  }

  render() {
    const style_banner = {
      backgroundColor: '#00ce81',
      height: '250px'
    }

    switch (this.props.page) {
      case "main":
      case "followings":
        console.log("main");
        //console.log("PROPS.USER from NavigationPannel:" + JSON.stringify(this.props.user));
        return (
        <div>
          <Helmet bodyAttributes={{style: 'padding-top: 50px;background-color : #E0E0E0'}}/>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
          <Nav
            isConnected={this.props.isConnected}
            logout={this.props.logout}
            user={this.props.user}
            viewPage={this.viewPage}

            requestProfile={this.requestProfile}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
            displaySettings={this.props.displaySettings}
          />
          <div class="col-md-8 col-md-offset-2 hidden-xs" style={style_banner}>BANNIERE</div>
          <UserNav
            user={this.props.user}
            page={this.state.page}
            viewPage={this.viewPage}

            requestedProfile={this.state.requestedProfile}
            userProfile={this.state.userProfile}
            requestProfile={this.requestProfile}
            viewProfile={this.viewProfile}

            follow={this.follow}
            unfollow={this.unfollow}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
          />
          <Content
            user={this.props.user}
            page={this.state.page}
            viewPage={this.viewPage}

            requestedProfile={this.state.requestedProfile}
            userProfile={this.state.userProfile}
            requestProfile={this.requestProfile}
            
            follow={this.follow}
            unfollow={this.unfollow}

            displayMain={this.props.displayMain}
          />
        </div>
        );
        break;
      case "profile":
        console.log("profile");
        return (
        <div>
          <Helmet bodyAttributes={{style: 'padding-top: 50px;background-color : #E0E0E0'}}/>
          <Nav
            isConnected={this.props.isConnected}
            logout={this.props.logout}
            user={this.props.user}
            viewPage={this.viewPage}

            requestProfile={this.requestProfile}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
            displaySettings={this.props.displaySettings}
          />
          <div class="col-md-8 col-md-offset-2 hidden-xs" style={style_banner}>BANNIERE</div>
          <UserNav
            user={this.props.user}
            page={this.state.page}
            viewPage={this.viewPage}

            requestedProfile={this.state.requestedProfile}
            userProfile={this.state.userProfile}
            requestProfile={this.requestProfile}
            viewProfile={this.viewProfile}

            follow={this.follow}
            unfollow={this.unfollow}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
          />
          <Profile
            user={this.props.user}
            page={this.state.page}
            viewPage={this.viewPage} 
            
            requestProfile={this.requestProfile}
            displayProfile={this.props.displayProfile}
          />
        </div>
        );
        break;
      case "settings":
        console.log("settings");
        return (
        <div>
          <Helmet bodyAttributes={{style: 'padding-top: 50px;background-color : #E0E0E0'}}/>
          <Nav
            isConnected={this.props.isConnected}
            logout={this.props.logout}
            user={this.props.user}
            viewPage={this.viewPage}

            requestProfile={this.requestProfile}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
            displaySettings={this.props.displaySettings}
          />
          <div class="col-md-8 col-md-offset-2 hidden-xs" style={style_banner}>BANNIERE</div>
          <UserNav
            user={this.props.user}
            page={this.state.page}
            viewPage={this.viewPage}

            displayMain={this.props.displayMain}
            displayProfile={this.props.displayProfile}
          />
          <div class="col-lg-12">SETTINGS</div>
        </div>
        );
        break;
      case "register":
        console.log("register");
        return (
        <div>
          <Nav
            isConnected={this.props.isConnected}
            displayMain={this.props.displayMain}
          />
          <Signin register={this.props.register} displayLogin={this.props.displayLogin} />
        </div>
        );
        break;
      case "connexion":
       console.log("connexion");
        return (
        <div>
          <Nav
            isConnected={this.props.isConnected}
            displayMain={this.props.displayMain}
          />
          <Login loginError={this.props.loginError} login={this.props.login} userCheck={this.props.userCheck} displayRegister={this.props.displayRegister} />
        </div>
        );
        break;
     }

  }

}

export default NavigationPannel;
