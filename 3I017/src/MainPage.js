import React, { Component } from 'react';
import ls from 'local-storage';
import Helmet from 'react-helmet';
import axios from 'axios';
import NavigationPannel from './NavigationPannel';

import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
window.jQuery = window.$ = require('../node_modules/jquery/dist/jquery.min.js');
require('../node_modules/bootstrap/dist/js/bootstrap.min.js');

class MainPage extends Component {

////////////////////////////////////////////////////////////////////////
//  TODO:
//-Refaire les sessions avec LS
//-Corriger le problème avec le login (passer par register)
//-Revoir la partie serveur
//-Composants Profil/Paramètres
//-Mettre l'adresse du serv en variable globale pour les axios
//-(Prendre une nouvelle version de FontAwesome)
//
// En fonction du temps :
//-Système de notifications
//-Système de MP
//
////////////////////////////////////////////////////////////////////////


  getConnected(login, password, lg){
   //ls.set('userPage', 'main');
   axios.get("http://localhost:8080/project4/login?user=" + login + "&pass=" + password).then((response) => {
   
   //console.log(response.data);
    lg(response);
    
   
   }).catch(error => {console.log(error)});
  }
  
  getRegistered() {
    //inscription
  }
  
  setLogout() {
    this.setState({isConnected: false,
    page: "connexion",
    loginError: false,
    user: {}
    });
    
    ls.clear();
  }
  
  userCheck(rep) {
    if ("erreur" in rep.data || !("key" in rep.data)) {
      this.setState({loginError: true});
      return;
      }

    this.setState({
      isConnected: true,
      page: "main",
      loginError: false,
      user: {
        id: rep.data.id,
        login: rep.data.login,
        key: rep.data.key
      }});
      
    this.exportSession(rep.data.id, rep.data.login, rep.data.key);
  }
  
  displayMain() {
    this.setState({page: "main"});
    ls.set('userPage', 'main');
  }
  
  displayProfile() {
    this.setState({page: "profile"});
    ls.set('userPage', 'profile');
  }
  
  displaySettings() {
    this.setState({page: "settings"});
    ls.set('userPage', 'settings');
  }
  
  displayRegister() {
    this.setState({page: "register", loginError: false});
  }
  
  displayLogin() {
    this.setState({page: "connexion"});
  }
  
  display(p) {
    this.setState({page: p});
    ls.set('userPage', p);
  }
  
  importSession() {
    //if (ls.get('userId') != null && ls.get('userLogin') != null && ls.get('userKey'))
      this.setState({
      //isConnected: true,
      //page: "main",
      //loginError: false,
      user: {
        id: 3,
        login: 2,
        key: 1
      }});
    //alert(JSON.stringify(this.state));
    this.state.user.id = ls.get('userId');
    this.state.user.login = ls.get('userLogin');
    this.state.user.key = ls.get('userKey');
    console.log("STATE from MainPage:" + JSON.stringify(this.state));
  }
  
  exportSession(id, login, key) {
    ls.set('userId', id);
    ls.set('userLogin', login);
    ls.set('userKey', key);
  }
  
  constructor(){
    super();
    this.getConnected = this.getConnected.bind(this);
    this.getRegistered = this.getRegistered.bind(this);
    this.setLogout = this.setLogout.bind(this);
    
    this.userCheck = this.userCheck.bind(this);
    this.importSession = this.importSession.bind(this);
    this.exportSession = this.exportSession.bind(this);
    
    this.displayMain = this.displayMain.bind(this);
    this.displayProfile = this.displayProfile.bind(this);
    this.displaySettings = this.displaySettings.bind(this);
    this.displayRegister = this.displayRegister.bind(this);
    this.displayLogin = this.displayLogin.bind(this);
    this.display = this.display.bind(this);
    
    if (ls.get('userId') == null || ls.get('userLogin') == null || ls.get('userKey') == null)
      this.state = {isConnected: false, page: "connexion", loginError: false, user: {}};
    else {
      this.state = {isConnected: true, page: "main", loginError: false, user: {}};
      this.importSession();
      }
  }
   
  //componentDidMount() {
    //
  //}
  //componentWillUnMount() {
    //export du state dans le LS
  //}
  
  
  render() {
    return (
      <NavigationPannel
        isConnected={this.state.isConnected}
        page={this.state.page}
        loginError={this.state.loginError}
        user={this.state.user}
        
        login={this.getConnected}
        logout={this.setLogout}
        
        userCheck={this.userCheck}
        
        displayMain={this.displayMain}
        displayProfile={this.displayProfile}
        displaySettings={this.displaySettings}
        displayRegister={this.displayRegister}
        displayLogin={this.displayLogin}
        update={this.update}
      />
    );    
  }

  
}

export default MainPage;
