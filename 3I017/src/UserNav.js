import React, { Component } from 'react';
import axios from 'axios';
import ls from 'local-storage';
import './css/UserNav.css';

class UserNav extends Component {
  
  constructor(props){
    super(props);
    this.state = {
    button: (<button class="editProfile" onClick={()=>{this.props.viewPage("profile");}}>Editer mon profil</button>),
    selected: "edit"
    };
   }
  
  
  render() {
    //AJOUTER CAS USER.LOGIN
    if (this.props.requestedProfile != this.props.userProfile) {
    
      if (this.props.requestedProfile != this.props.user.login) {
      
      axios.get("http://localhost:8080/project4/getProfile?user=" + this.props.requestedProfile).then(response => {
      
        if (JSON.stringify(response.data) != JSON.stringify({})) { //l'utilisateur existe
          this.props.viewProfile(this.props.requestedProfile);
          
          axios.get("http://localhost:8080/project4/getFollowing?key=" + this.props.user.key).then(rep => {    

            if (rep.data.indexOf(this.props.requestedProfile) >= 0) {
              this.setState({button: (<button class="followedProfile" onClick={() => {this.props.unfollow(this.props.user.key, this.props.requestedProfile)}}>Suivi</button>), selected: "followed"});
            } else {
              this.setState({button: (<button class="followProfile" onClick={() => this.props.follow(this.props.user.key, this.props.requestedProfile)}>Suivre @{this.props.userProfile}</button>), selected: "follow"});
            }
            
          }).catch(error => {console.log(error)});
          
        } else {
          this.props.requestProfile(this.props.userProfile);
        }

      }).catch(error => {console.log(error)});
      
      } else { //afficher son propre profile
        this.props.viewProfile(this.props.requestedProfile);
        this.setState({button: (<button class="editProfile" onClick={()=>{this.props.viewPage("profile");}}>Editer mon profil</button>)});
      }
      
    }
    
    const msg = () => <li><a href="#" onClick={()=>{this.props.viewProfile(this.props.user.login); this.props.viewPage("main"); this.props.displayMain();}}>Messages</a></li>;
    const msg_active = () => <li class="active"><a href="#" onClick={()=>{this.props.viewPage("main"); this.props.displayMain()}}>Messages</a></li>;
    
    const follow = () => <li><a href="#" onClick={()=>{this.props.viewPage("followings")}}>Abonnements</a></li>;
    const follow_active = () => <li class="active"><a href="#" onClick={()=>{this.props.viewPage("followings")}}>Abonnements</a></li>;
    
    const follower = () => <li><a href="#" onClick={()=>{this.props.viewPage("followers")}}>Abonnés</a></li>;
    const follower_active = () => <li class="active"><a href="#" onClick={()=>{this.props.viewPage("followers")}}>Abonnés</a></li>;
    
    const like = () => <li><a href="#" onClick={()=>{this.props.viewPage("likes")}}>J'aime</a></li>;
    const like_active = () => <li class="active"><a href="#" onClick={()=>{this.props.viewPage("likes")}}>J'aime</a></li>;
    
    
    const msg_display = this.props.page == "main" ? msg_active : msg;
    const follow_display = this.props.page == "followings" ? follow_active : follow;
    const follower_display = this.props.page == "followers" ? follower_active : follower;
    const like_display = this.props.page == "likes" ? like_active : like;
    
    return (
  <div class="col-xs-12 profilNav">
    <ul class="col-sm-6 col-sm-offset-3 col-lg-5 col-lg-offset-4 profilButtonBar">
      {msg_display()}
      {follow_display()}
      {follower_display()}
      {like_display()}
    </ul>
    {this.state.button}
  </div>
  );
  }
  
}

export default UserNav;
