import React, { Component } from 'react';
import axios from 'axios';
import './css/Login.css';

class Signin extends Component {
  prenom = "";
  nom = "";
  login = "";
  mail = "";
  mdp1 = "";
  mdp2 = "";
  
  constructor(){
    super();
    this.register = this.register.bind(this);
   }
   
  register() {
    if (this.mdp1 == "" || this.mdp2 != this.mdp1) {
      alert("Les deux mdp doivent etre identiques");
      return;
      }
    if (this.prenom != "" && this.nom != "" && this.login != "" && this.mail != "") {
      axios.get("http://localhost:8080/project4/register?nom=" + this.nom + "&prenom=" + this.prenom + "&mdp=" + this.mdp1 + "&mail=" + this.mail + "&login=" + this.login).then((response) => {
        alert("inscrit");
      }).catch(error => {console.log(error)});
  
    
    }
  }
  
  render() {
    return (
    <div class="modal-dialog form-login">
      <div class="modal-content">
      
        <div class="modal-header">
          <h4 class="modal-title">Inscription</h4>
        </div>
        
        <div class="modal-body">
          <form action="" method="post">
            <fieldset>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Prénom" required="required" onInput={(evt)=> { this.prenom = evt.target.value;}} />
              </div>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Nom" required="required" onInput={(evt)=> { this.nom = evt.target.value;}} />
              </div>
            </fieldset>
            
            <fieldset>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Login" required="required" onInput={(evt)=> { this.login = evt.target.value;}} />
              </div>
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Adresse email" required="required" onInput={(evt)=> { this.mail = evt.target.value;}} />
              </div>
              <div class="form-group">
                <input type="password" class="form-control" placeholder="Mot de passe" required="required" onInput={(evt)=> { this.mdp1 = evt.target.value;}} />
              </div>
              <div class="form-group">
                
                <input type="password" class="form-control" placeholder="Retapez le mot de passe" required="required" onInput={(evt)=> { this.mdp2 = evt.target.value;}} />
              </div>
              <div class="form-group">
                <input type="submit" class="btn btn-primary btn-block btn-lg" value="S'inscrire" onClick={() => {this.register(this.prenom, this.nom, this.login, this.mail, this.mdp1, this.mdp2); this.props.displayLogin();}} />
              </div>
            </fieldset>
          </form>
        </div>
        
        <div class="modal-footer">
          <a href="#" onClick={()=>this.props.displayLogin()}>Annuler</a>
        </div>
        
      </div>
    </div>
    );
  }
  
}

export default Signin;
