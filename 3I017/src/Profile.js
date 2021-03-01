import React, { Component } from 'react';
import axios from 'axios';
import './css/Profile.css';

class Profile extends Component {
  nom = null;
  prenom = null;
  //login = null;
  description = null;
  color = null;
  //ajouter la pp
  
  constructor(props){
    super(props);
    this.getProfile = this.getProfile.bind(this);
    
    this.state = {profile: []};
   }
   
   getProfile() {
    axios.get("http://localhost:8080/project4/getProfile?user=" + this.props.user.login).then(response => {
      this.state.profile = response.data;
      this.setState({});
    }).catch(error => {console.log(error)});
   }
   
   updateProfile() {
         alert("http://localhost:8080/project4/update?key=" + this.props.user.key + "&prenom=" + this.prenom + "&nom=" + this.nom + "&color=" + this.color + "&desc=" + this.description);
   
    axios.get("http://localhost:8080/project4/update?key=" + this.props.user.key + "&prenom=" + this.prenom + "&nom=" + this.nom + "&color=" + this.color + "&desc=" + this.description).then(response => {
      
      //alert("http://localhost:8080/project4/update?key=" + this.props.user.key + "&prenom=" + this.prenom + "&nom=" + this.nom + "&color=" + this.color + "&desc=" + this.description);
      
    }).catch(error => {console.log(error)});
   }
  
  componentWillMount() {
    this.getProfile();
  }
  
  render() {
  
    const displayProfile = (
        <div class="profile col-sm-8 col-sm-offset-2">
          <form>
            <h3>Modifier mon profil</h3>
            <div>
              <div class="profile-left">
                  <p><span class="labl">Prénom</span></p>
                  <p><span class="labl">Nom</span></p>
                  <p><span class="labl">Login</span></p>
              </div>
              <div>
                <fieldset>
                  <p>{this.state.profile.prenom}</p>
                  <p>{this.state.profile.nom}</p>
                  <p>{"@" + this.state.profile.login}</p>
                </fieldset>
              </div>
            </div>
            <hr />
            <div>
              <div class="profile-left">
                <p><span class="labl">Photo</span></p>
                <p><span class="labl">Couleur</span></p>
              </div>
              <div>
                <fieldset>
                  <p>PP</p>
                  <p>{this.state.profile.color}</p>
                </fieldset>
              </div>
              <hr />
              <fieldset>
                <input type="submit" value="Modifier" onClick={() => {this.props.viewPage("content-edit")}} />
              </fieldset>
            </div>
          </form>
        </div>
    );
  
    const editProfile = (
        <div class="profile col-sm-8 col-sm-offset-2">
          <form>
            <h3>Modifier mon profil</h3>
            <div>
              <div class="profile-left">
                  <p><span class="labl">Prénom</span></p>
                  <p><span class="labl">Nom</span></p>
                  <p><span class="labl">Login</span></p>
              </div>
              <div>
                <fieldset>
                  <p><input type="text" placeholder={this.state.profile.prenom} onInput={(evt)=> { this.prenom = evt.target.value;}} /></p>
                  <p><input type="text" placeholder={this.state.profile.nom} onInput={(evt)=> { this.nom = evt.target.value;}}  /></p>
                  <p><input type="text" placeholder={"@" + this.state.profile.login} disabled /></p>
                </fieldset>
              </div>
            </div>
            <hr />
            <div>
              <div class="profile-left">
                <p><span class="labl">Photo</span></p>
                <p><span class="labl">Couleur</span></p>
              </div>
              <div>
                <fieldset>
                  <p><input type="text" onInput={(evt)=> { this.userpp = evt.target.value;}}  /></p>
                  <p><input type="text" placeholder={this.state.profile.color} onInput={(evt)=> { this.color = evt.target.value;}}  /></p>
                </fieldset>
              </div>
              <hr />
              <fieldset>
                <input type="submit" value="Enregistrer" onClick={() => {this.updateProfile(); this.props.viewPage("content-display")}} />
              </fieldset>
            </div>
          </form>
        </div>
    );
    
    return (
    <div class="tweet col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2">
      <div class="profile-content">
        {this.props.page == "content-edit" ? editProfile : displayProfile}
      </div>
    </div>
    );
  }
  
}

export default Profile;
