import React, { Component } from 'react';
import ls from 'local-storage';
import './css/Nav.css';

class Nav extends Component {
  userName_input = null;

  constructor(props){
    super(props);
   }

  render() {
    const notificationButton = this.props.isConnected ? (
    <li><a href="#">Notifications <span class="badge">7</span></a></li>
    ) : null;

    const messageButton = this.props.isConnected ? (
    <li><a href="#">Messages <span class="badge">3</span></a></li>
    ) : null;

    const userButton = (this.props.isConnected && this.props.user != null) ? (
    <li class="dropdown">
      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><strong>{this.props.user.login}</strong> <span class="caret"></span></a>
      <ul class="dropdown-menu">
        <li class="dropdown-header">@{this.props.user.login}</li>
        <li><a href="#"  onClick={()=>this.props.displayProfile()}>Profil</a></li>
        <li><a href="#">Abonnements</a></li>
        <li><a href="#" onClick={()=>this.props.displaySettings()}>Paramètres</a></li>
        <li role="separator" class="divider"></li>
        <li><a href="#" onClick={()=>this.props.logout()}>Déconnexion</a></li>
      </ul>
    </li>
    ) : null;

    return (
    <nav class="navbar navbar-fixed-top navbar-default">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">COULY</a>
        </div>

        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#"  onClick={()=>{this.props.requestProfile(this.props.user.login); this.props.viewPage("main"); this.props.displayMain()}}><i class="fa fa-home"></i>  Accueil</a></li>
            {notificationButton}
            {messageButton}
          </ul>

          <ul class="nav navbar-nav navbar-right">
            <li>
              <div class="input-group search">
                <input type="text" class="form-control search_bar" placeholder="Rechercher" onInput={(evt)=> { this.userName_input = evt.target.value;}}/>
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button" onClick={()=>{
      if (this.userName_input != null && this.userName_input != "") {
        this.props.requestProfile(this.userName_input);
        this.props.viewPage("main");
      }
      }}><span class="glyphicon glyphicon-search search_button"></span></button>
                </span>
              </div>
            </li>
            {userButton}
          </ul>
        </div>
      </div>
    </nav>
    );
  }

}

export default Nav;
