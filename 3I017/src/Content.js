import React, { Component } from 'react';
import axios from 'axios';
import './css/Content.css';
import userPP from './user.jpeg';

class Content extends Component {
  comment_input = null;
  reply_input = null;
  replyTo = null;

  constructor(props){
    super(props);
    this.state = {messages: [], followings: [], followers: [], rep: []};
   }
   
   like(user_key, message_id) {
    //alert(user_key + "--" + message_id);
    axios.get("http://localhost:8080/project4/like?key=" + user_key + "&message=" + message_id).then(rep => {
      console.log("http://localhost:8080/project4/like?key=" + user_key + "&message=" + message_id);
    }).catch(error => {console.log(error)});
    
   }

  postMessage(message,display) {
    if (this.comment_input == null || this.comment_input == "")
      alert("Vous ne pouvez pas poster de message vide");
    else {
      axios.get("http://localhost:8080/project4/addComment?key=" + this.props.user.key + "&message=" + message).then(rep => {}).catch(error => {console.log(error)});

      this.comment_input = null;
      this.refs.newMsg.value = "";
      this.getMsg(this.props.userProfile); //on raffraichit la liste des messages dans le state
    }
  }
  
  reply() {
    if (this.reply_input == null)
      alert("Vous ne pouvez pas poster de réponse vide");
    else {
      axios.get("http://localhost:8080/project4/reply?key=" + this.props.user.key + "&message=" + this.reply_input + "&replyto=" + this.replyTo).then(rep => {}).catch(error => {console.log(error)});
      
      this.reply_input = null;
      this.refs.newRep.value = "";
      //this.getReply(this.msg_id);
    }
  }

  deleteMessage(user_key, message_id) {
    axios.get("http://localhost:8080/project4/removeComment?key=" + user_key + "&message=" + message_id).then(rep => {}).catch(error => {console.log(error)});

    this.getMsg(this.props.user.login);
  }
  
  displayReply(reply) {
    return (
    <div>
    <br /><br />OPEHGK<br />
    </div>
    );
  }

  displayMessages(msg) {
    if (this.props.requestedProfile != this.props.userProfile) {
      this.getMsg(this.props.requestedProfile);
      this.getFollowings(this.props.requestedProfile);
      this.getFollowers(this.props.requestedProfile);
      }
    
    console.log(JSON.stringify(msg));
      
    return (
      <div class="tweet col-xs-12 col-md-10 col-md-offset-1">
        <div class="userPP col-xs-2"><img src="https://s3.amazonaws.com/uifaces/faces/twitter/dancounsell/73.jpg" width="150px" height="150px" class="img-circle pp" /></div>
        <div class="col-xs-10 content">
          <div class="tweet_header">
            <a href="#" onClick={() => {}}><strong>{msg.login}</strong></a> @{msg.login} · 5h
            {msg.login == this.props.user.login ? (
            <div class="userActions dropdown">
              <span class="caret dropdown-toggle" data-toggle="dropdown"></span>
              <ul class="dropdown-menu">
                <li><a href="#" onClick={() => {alert("edit");}}>Editer</a></li>
                <li><a href="#" onClick={() => {
                if (window.confirm("Supprimer ce message ?"))
                  this.deleteMessage(this.props.user.key, msg._id)}
                }>Supprimer</a></li>
              </ul>
            </div>
            ) : null}
          </div>
          <div class="tweet_body">
            <p>
              {msg.text}
              <br />id: {msg._id}
              <br />userkey: {this.props.user.key}
            </p>
          </div>
          <div class="tweet_footer">
            <i class="fa fa-comment"></i> 3   <i class="fa fa-retweet"></i> 5   <a href="#" onClick={() => this.like(this.props.user.key, msg._id)}><i class="fa fa-heart"></i></a> {msg.nbLike}   <i class="fa fa-envelope"></i>
            <span class="userActions"><a href="#" data-toggle="collapse" data-target="#display">+</a></span>
            <div id="display" class="collapse">
              <form>
              <input type="text" ref="newRep" onInput={(evt)=> { this.reply_input = evt.target.value;}} /><input type="submit" onClick={() => {this.replyTo = msg._id; this.reply(this.reply_input)}} />
              </form>
              {this.state.rep.map(x => this.displayReply(x))}
            </div>
          </div>
        </div>
        <div id="display-reply" class="collapse">
          <form><input type="text" ref="newRep" onInput={(evt)=> { this.reply_input = evt.target.value;}} /><input type="submit" onClick={() => {this.replyTo = msg._id; this.reply(this.reply_input)}} /></form>
        </div>
      </div>
      );
  }

  displayFollowings(following) {
      let btn_follow;
      switch (following.doesFollow) {
        case "self":
          btn_follow = (<button class="editProfile" onClick={()=>{
          this.props.requestProfile(this.props.user.login);
          this.props.viewPage("main")
          this.props.viewPage("profile");
          }}>Editer mon profil</button>);
          break;
        case "followed":
          btn_follow = (<button class="followedProfile" onClick={() => {
          this.props.unfollow(this.props.user.key, following.login);
          this.getFollowings(this.props.userProfile);
          }}>Suivi</button>);
          break;
        case "notfollowed":
          btn_follow = (<button class="followProfile" onClick={() => {
          this.props.follow(this.props.user.key, following.login);
          this.getFollowings(this.props.userProfile);
          }}>Suivre</button>);
          break;
      }
  
    return (
        <div class="col-sm-6 col-md-4 follow">
          
          <div class="follow-header" style={{backgroundColor: following.color}}>
            <span class="follow-pp"><img src={following.userpp} class="img-circle pp" /></span>
          </div>
          
          <div class="follow-bar">
            <span class="follow-button">{btn_follow}</span>
          </div>
          
          <div class="follow-name">
            <a href="#" onClick={() => {
            this.props.requestProfile(following.login);
            this.props.viewPage("main");
            }}><strong>{following.nom == null ? following.prenom : following.prenom + " - " + following.nom}</strong></a>
            <br />
            <em>@{following.login}</em>
          </div>
          
          <div class="follow-body">
            {following.description}
          </div>
        </div>
      );
  }

  displayFollowers(follower) {
      let btn_follow;
      switch (follower.doesFollow) {
        case "self":
          btn_follow = (<button class="editProfile" onClick={()=>{this.props.viewPage("profile");}}>Editer mon profil</button>);
          break;
        case "followed":
          btn_follow = (<button class="followedProfile" onClick={() => {
          this.props.unfollow(this.props.user.key, follower.login);
          this.getFollowers(this.props.userProfile);}
          }>Suivi</button>);
          break;
        case "notfollowed":
          btn_follow = (<button class="followProfile" onClick={() => {
          this.props.follow(this.props.user.key, follower.login);
          this.getFollowers(this.props.userProfile);
          }}>Suivre</button>);
          break;
      }
      
      return (
        <div class="col-sm-6 col-md-4 follow">
          
          <div class="follow-header" style={{backgroundColor: follower.color}}>
            <span class="follow-pp"><img src={follower.userpp} class="img-circle pp" /></span>
          </div>
          
          <div class="follow-bar">
            <span class="follow-button">{btn_follow}</span>
          </div>
          
          <div class="follow-name">
            <a href="#" onClick={() => {
            this.props.requestProfile(follower.login);
            this.props.viewPage("main");
            }}><strong>{follower.login}</strong></a>
            <br />
            <em>@{follower.login}</em>
          </div>
          
          <div class="follow-body">
            {follower.description}
          </div>
          
        </div>
      );
  }

  getMsg(user) {
    axios.get("http://localhost:8080/project4/messages?user=" + user).then((response) => {
    var l = [];
    for (const i in response.data)
      l.push(response.data[i]);

    this.state.messages = l;
    this.setState({});

    }).catch(error => {console.log(error)});
  }
  
  getReply(msg_id) {
    axios.get("http://localhost:8080/project4/messages?key=" + this.props.user.key + "&message=" + msg_id).then((response) => {
    var l = [];
    for (const i in response.data)
      l.push(response.data[i]);

    this.state.rep = l;
    this.setState({});

    }).catch(error => {console.log(error)});
  }

  getFollowings(user) {
    axios.get("http://localhost:8080/project4/getFollowing?user=" + user + "&display=full").then((response) => {
    
      axios.get("http://localhost:8080/project4/getFollowing?key=" + this.props.user.key).then((response2) => {
    
      var l = [];
      
      for (const i in response.data) {
        
        let profile;
        
        if (response.data[i].login == this.props.user.login)
          profile = Object.assign(response.data[i], {doesFollow: "self"});
        else if (response2.data.indexOf(response.data[i].login) >= 0)
          profile = Object.assign(response.data[i], {doesFollow: "followed"});
        else
          profile = Object.assign(response.data[i], {doesFollow: "notfollowed"});
        
        l.push(profile);
        
        }

      this.state.followings = l;
      this.setState({});
      
      });

    }).catch(error => {console.log(error)});
  }

  getFollowers(user) {
    axios.get("http://localhost:8080/project4/getFollower?user=" + user + "&display=full").then((response) => {
    
      axios.get("http://localhost:8080/project4/getFollowing?key=" + this.props.user.key).then((response2) => {
    
      var l = [];
      for (const i in response.data) {
      
        let profile;
        
        if (response.data[i].login == this.props.user.login)
          profile = Object.assign(response.data[i], {doesFollow: "self"});
        else if (response2.data.indexOf(response.data[i].login) >= 0)
          profile = Object.assign(response.data[i], {doesFollow: "followed"});
        else
          profile = Object.assign(response.data[i], {doesFollow: "notfollowed"});
        
        l.push(profile);
        
        }

      this.state.followers = l;
      this.setState({});
      
      });
    
    
    

    }).catch(error => {console.log(error)});
  }

  componentWillMount() {
    this.getMsg(this.props.user.login);
    this.getFollowings(this.props.user.login);
    this.getFollowers(this.props.user.login);

    //alert("State>>"+JSON.stringify(this.state));
    //alert("Props>>"+JSON.stringify(this.props));
    //this.getLikes();
  }

  render() {
    console.log("PROPS from Main" + JSON.stringify(this.props));

    let content;
    switch(this.props.page) {
      case "main":
        content = this.props.requestedProfile == this.props.user.login ? (
          <div class="contenu">
            <div class="newMessage">
              <label for="newMsg">Quoi de neuf ?</label>
              <textarea class="form-control" id="newMsg" ref="newMsg" placeholder="Votre petite pensée du jour" onInput={(evt)=> { this.comment_input = evt.target.value;}}></textarea>
              <br />
              <button class="btn btn-primary" type="submit" onClick={() => this.postMessage(this.comment_input,this.props.displayMain)}>S'exprimer</button>
            </div>
            <br />Mon flux :
            <div class="listMessages">
              {this.state.messages.map(x => this.displayMessages(x))}
            </div>
          </div>
        ) : (
          <div class="contenu">
            {this.state.messages.map(x => this.displayMessages(x))}
          </div>
        );
        break;
      case "followings":
        content = this.state.followings.map(x => this.displayFollowings(x));
        break;
      case "followers":
        content = this.state.followers.map(x => this.displayFollowers(x));
        break;
      case "likes":
        content = (
          <p>LIKES</p>
        );
        break;
    }

    return (
<div class="fullcontent">
  <div class="hidden-xs col-sm-2 col-sm-offset-1 col-md-offset-2 stats_panel">
    <img src={userPP} width="150px" height="150px" class="img-circle pp" />
    <div class="feoj">
      <h4>{this.props.userProfile}</h4>
      <ul >
        <li>@{this.props.userProfile}</li>
        <li><a href="#">url.cf</a></li>
        <li>Date d'inscription</li>
        <li>Page : {this.props.page}</li>
      </ul>
    </div>
  </div>
  <div class="col-xs-12 col-sm-8 col-md-6 messages_panel">
    {content}
    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
  </div>
</div>
    );
  }

}

export default Content;
