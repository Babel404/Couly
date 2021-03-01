import React, { Component } from 'react';
import './css/Login.css';

class Login extends Component {
  login_input = null;
  password_input = null;
  
  //on peut aussi utiliser les "ref" à la place
  
  constructor(props){
    super(props);
   }
   
  render() {
    const errorMessage = this.props.loginError ? (
    <div class="form-group loginError">
      <i class="fa fa-exclamation-triangle"></i>
      Le nom d'utilisateur et le mot de passe indiqués ne correspondent pas.
    </div>
    ) : null;
    
    return (
    <div class="modal-dialog form-login">
      <div class="modal-content">
    
        <div class="modal-header">
          <h4 class="modal-title">Ouvrir une session</h4>
        </div>
      
        <div class="modal-body">
          <form action="">
            <div class="form-group">
              <i class="fa fa-user"></i>
              <input type="text" class="form-control" placeholder="Login" required="required" onInput={(evt)=> { this.login_input = evt.target.value;}} />
            </div>
            <div class="form-group">
              <i class="fa fa-lock"></i>
              <input type="password" class="form-control" placeholder="Mot de passe" required="required" onInput={(evt)=> { this.password_input = evt.target.value;}} />
            </div>
            <div class="form-group">
              <input type="submit" class="btn btn-primary btn-block btn-lg" value="Connexion" onClick={()=>{this.props.login(this.login_input,this.password_input,this.props.userCheck);}} />
            </div>
            {errorMessage}
          </form>
        </div>
      
        <div class="modal-footer">
          <a href="#" onClick={()=>this.props.displayRegister()}>Inscription</a>
        </div>
      
      </div>
    </div>
    );
  }
  
}

export default Login;
