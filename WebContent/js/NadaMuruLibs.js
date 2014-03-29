// Save the key into the API Storage HTML5
function saveKey(cle){

	if(window.localStorage){
		deleteKey();
		localStorage.setItem('CleSession',cle);
	}
	else{
		alert("Erreur pas de gestion du LocalStorage");	
	} 
}

// Get the key Session
function getKey(){
	return localStorage.getItem('CleSession');
}

// Delete the key Session
function deleteKey(){
	localStorage.removeItem('CleSession');
}

// Logout a client
function logout(){
	deleteKey();
	$(location).attr('href',"index.html");
}


// Check if a client can be connected
function checkRoutineMain(){
	if(getKey() == undefined){
		logout();
	}
}


//Check if a client can be connected
function checkRoutineIndex(){
	if(getKey() != undefined){
		$(location).attr('href',"main.html");
	}
}

// Realize Fade In/Out of the Form (login/register...)
function fadeManager(form,replaceText){
		  form.hide(1000);
		  form.replaceWith(replaceText);
		  form.hide();
		  form.show(1000);	
}

// Take decision about Connexion (error msg, make redirection ...) with a JSON
function processingConnexionJSON(JSONString,block){

	var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
	
	if(codeHTTP == "200"){
		var cle = $.parseJSON(JSONString).Key;
		if(cle != undefined){
			saveKey(cle);
			$(location).attr('href',"main.html");
		}
	}
	else if(codeHTTP == "400") {
		var erreurMsg=$.parseJSON(JSONString).Message;
		
		var div='<div class="alert alert-block alert-error fade in">';
		div+='<button class="close" data-dismiss="alert" type="button">x</button>';
		div+='<h4 class="alert-heading">Erreur '+codeHTTP+'</h4>';
		div+='<p>'+erreurMsg+'</p></div>';
	
		block.prepend(div);
		
	}
	
	
}



//Take decision about Add comment (error msg, make redirection ...) with a JSON
function processingAddCommentJSON(JSONString){
	
	var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
	
	if(codeHTTP == "200"){
		alert("Success");
	}
	else if(codeHTTP == "400") {
		alert( $.parseJSON(JSONString).Message);
	}

}

//Take decision about logout (error msg, make redirection ...) with a JSON
function processingLogoutJSON(JSONString){
	
	var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
	
	if(codeHTTP == "200"){
		logout();
	}
	else if(codeHTTP == "400") {
		alert( $.parseJSON(JSONString).Message);


	
	}

}

function processingRegisterJSON(JSONString,block){
	
	var codeHTTP = $.parseJSON(JSONString).CodeHTTP;

	if(codeHTTP == "200"){
		
		var div='<div class="alert fade in">';
		div+='<button class="close" data-dismiss="alert" type="button">x</button>';
		div+='<strong>Votre compte a bien été enregistré.</strong></div>';
	
		block.prepend(div);
		
	}
	else if(codeHTTP == "400") {
		var erreurMsg=$.parseJSON(JSONString).Message;
		
		var div='<div class="alert alert-block alert-error fade in">';
		div+='<button class="close" data-dismiss="alert" type="button">x</button>';
		div+='<h4 class="alert-heading">Erreur '+codeHTTP+'</h4>';
		div+='<p>'+erreurMsg+'</p></div>';
	
		block.prepend(div);
	}
	
}





function showPostComment(postDiv,msgField){
	  postDiv.submit(function(e){ 

		  e.preventDefault();	  

		  var msg=msgField.val();	
	 	  var param="text="+msg+"&"+"key="+getKey();

	 	  $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/addcomment",param,function(data){
	 		  
	 		 var jsonString = JSON.stringify(data);
	 		 processingAddCommentJSON(jsonString);

	 	  });
		  
	  });	
}

function createProfil(profilContainerDiv,comsContainerDiv){
	 $.ajaxSetup({async: false});
	 var param = "key="+getKey();
	 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
		var JSONString = JSON.stringify(data); 
		var id = $.parseJSON(JSONString).ID; 
	 	var param="id="+id+"&"+"key="+getKey();
	 	
	 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/namefromid",param,function(data){
		 		var template = '<div class="Stats"><h4>{{name}} {{firstName}} </h4> <img src="img/photo-profil-default.png" class="img-polaroid" ><br> ';
		 		template+='<br><a class="allMsg">Afficher tous les messages</a><br><a class="friendMsg">Afficher les messages de vos amis</a><br></div>';
				var output = Mustache.render(template, data);
				profilContainerDiv.prepend(output);		
	 	});
	 	
		$('.allMsg').click( function(){
			comsContainerDiv.empty();
			showAllMessage(comsContainerDiv);
		});
	 	
		
		$('.friendMsg').click( function(){
			comsContainerDiv.empty();
			showFriendMessage(comsContainerDiv);
		});
	 	
	 });
	 $.ajaxSetup({async: true});
}

function makeLogout(logoutButton){
	
	logoutButton.click( function(){
		 
		  var param="key="+getKey();

	 	  $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/logout",param,function(data){
	 		 var jsonString = JSON.stringify(data);
	 		 processingLogoutJSON(jsonString);
	 	  });	
		 
	 });
}

function showFriendList(FriendContainerDiv){
	 $.ajaxSetup({async: false});
	 var param = "key="+getKey();
	 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
		var JSONString = JSON.stringify(data); 
		var id = $.parseJSON(JSONString).ID; 
	 	var param="idA="+id;
	 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/friendlist",param,function(data){
		 		var template = '<div class="Stats amis"><h4>Liste d\'amis</h4><br></div>';
		 		FriendContainerDiv.append(template);
		 		
		 		var JSONString = JSON.stringify(data); 
		 		var tabid = $.parseJSON(JSONString).idFriendB; 


				for(var i=0; i<tabid.length; i++){
					
					var param="id="+tabid[i]+"&"+"key="+getKey();
					
				 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/namefromid",param,function(data){
			 			var tmp = "<p>{{name}} {{firstName}}</p>";
						var output = Mustache.render(tmp, data);
						$('.amis').append(output);
				 	});
					
				}
		 		
	 	});
	 });
	 $.ajaxSetup({async: true});
}



function showUserList(UserContainerDiv){
	 $.ajaxSetup({async: false});
	 var param = "key="+getKey();
	 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
		var JSONString = JSON.stringify(data); 
		var id = $.parseJSON(JSONString).ID; 
	 	var param="";
	 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/userlist",param,function(data){
		 		var template = '<div class="Stats user"><h4>User List</h4><br></div>';
		 		UserContainerDiv.append(template);
		 		
		 		var JSONString = JSON.stringify(data); 
		 		var tabid = $.parseJSON(JSONString).user; 


				for(var i=0; i<tabid.length; i++){
					
					var param="id="+tabid[i]+"&"+"key="+getKey();
					var id1 = tabid[i];
					if(id != id1){ // Pour eviter de rajouter l'user lui même
					 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/namefromid",param,function(data){
					 		var buttonValue ="";
					 		var param="idA="+id+"&"+"idB="+id1;
					 		$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/checkFriend",param,function(data){
						 		var JSONString = JSON.stringify(data); 
						 		var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
						 		
						 		//Test pour savoir la valeur du bouton (si c'est Add ou Remove)
						 		if(codeHTTP == "200"){
						 			buttonValue += "Add";
						 		}
						 		else{
						 			if(codeHTTP == "400"){
							 			buttonValue += "Remove";
							 			
							 		}
						 		}
						 	});
					 		var tmp = '<tr><td><button name="'+id1+'" class="btn btn-mini btn-info addButton" type="button">'+buttonValue+'</button></td><td>  </td><td><button name="'+id1+'" class="btn btn-mini btn-info commonFriendButton" type="button">Common Friend</button></td><td>{{name}} {{firstName}}</td></tr>';
							var output = Mustache.render(tmp, data);
							$('.user').append(output);
					 	});
					}
				}
		 		
	 	});
	 	$('.addButton').click( function(){
		 	var bouton = $(this);
			
			if(bouton.html()=="Add"){
		  	 	var key = getKey();
			   	var idAdd = $(this).attr("name");
				var param = "key="+key;
	  			 $.ajaxSetup({async: false});
				 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
			
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
		 			var param="idA="+idUser+"&"+"idB="+idAdd;
					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/addFriend",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
					
						if(codeHTTP == "200"){
							bouton.html("Remove");
						}
				 	});
				 });
			}
			else{
				if(bouton.html()=="Remove"){
			  	 	var key = getKey();
				   	var idAdd = $(this).attr("name");
					var param = "key="+key;
		  			 $.ajaxSetup({async: false});
			
					 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
				
						var JSONString = JSON.stringify(data); 
						var idUser = $.parseJSON(JSONString).ID; 
			 			var param="idA="+idUser+"&"+"idB="+idAdd;
						$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/deleteFriend",param,function(data){
							var JSONString = JSON.stringify(data); 
							var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
						
							if(codeHTTP == "200"){
								bouton.html("Add");
							}
					 	});
					 });
				}
			}
		});
	 	$('.commonFriendButton').click(function(){
	 		$('.amisCommun').hide(1000);
	 		var key = getKey();
		   	var idAdd = $(this).attr("name");
			var param = "key="+key;
 			 $.ajaxSetup({async: false});
			 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
		
				var JSONString = JSON.stringify(data); 
				var idUser = $.parseJSON(JSONString).ID; 
	 			var param="idA="+idUser+"&"+"idB="+idAdd;
	 			var param ="idA1="+idUser+"&"+"idA2="+idAdd; 
	 			$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/commonfriendlist",param,function(data){
		 		var template = '<div class="Stats amisCommun"><h4>Commun Friend List</h4><br></div>';
		 		UserContainerDiv.append(template);
		 		
		 		var JSONString = JSON.stringify(data); 
		 		var tabid = $.parseJSON(JSONString).idCommonFriend; 


				for(var i=0; i<tabid.length; i++){
					
					var param="id="+tabid[i]+"&"+"key="+getKey();
					
				 	$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/namefromid",param,function(data){
			 			var tmp = "<p>{{name}} {{firstName}}</p>";
						var output = Mustache.render(tmp, data);
						$('.amisCommun').replaceWith($('.amisCommun').append(output));

//						$('.amisCommun').append(output);
				 	});
				}
	 			});
	 	});
	 		
	 	});
	 });
	 $.ajaxSetup({async: true});
}



function showAllMessage(ContainerDiv){
	
	 $.ajaxSetup({async: false});	
	 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/searchwithoutquery",null,function(data){
		 
		for(var i=0; i<data.length; i++){
		
				var obj = data[i];
				var param = "key="+getKey();
				var JSONString = JSON.stringify(obj); 
				var idCom = $.parseJSON(JSONString)['_id'];
				$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
			 		var param="idUser="+idUser+"&"+"key="+getKey()+"&"+"idCom="+idCom;

					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/checklikeincomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
						
						if(codeHTTP == "200"){
							var template = '<div class="page-header Coms"><h4>{{name}} {{firstName}} <small>{{date}}</small></h4><pre>{{comment}}</pre> <button name="{{_id}}" class="btn btn-mini btn-info likeButton" type="button">Unlike</button></div>';
							var output = Mustache.render(template, obj);
							ContainerDiv.append(output).hide().fadeIn(1);
						}	
						else if(codeHTTP == "400") {
							var template = '<div class="page-header Coms"><h4>{{name}} {{firstName}} <small>{{date}}</small></h4><pre>{{comment}}</pre> <button name="{{_id}}" class="btn btn-mini btn-info likeButton" type="button">Like</button></div>';
							var output = Mustache.render(template, obj);
							ContainerDiv.append(output).hide().fadeIn(1);	

						}
					});
				});
		}
		
		$('.likeButton').click( function(){
		 	var bouton = $(this);
			
			if(bouton.html()=="Like"){
		  	 	var key = getKey();
			   	var idCom = $(this).attr("name");
				var param = "key="+key;
	  			 $.ajaxSetup({async: false});
		
				 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
			
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
		 			var param="idUser="+idUser+"&"+"key="+key+"&"+"idCom="+idCom;
					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/addlikecomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
					
						if(codeHTTP == "200"){
							bouton.html("Unlike");
						}
				 	});
				 });
			}
			else if (bouton.html()=="Unlike"){
		  	 	var key = getKey();
			   	var idCom = $(this).attr("name");
				var param = "key="+key;
	  			 $.ajaxSetup({async: false});
		
				 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
			
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
		 			var param="idUser="+idUser+"&"+"key="+key+"&"+"idCom="+idCom;
					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/removelikecomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
					
						if(codeHTTP == "200"){
							bouton.html("Like");
						}
				 	});
				 });
			}
			
		});
		
	 });
	$.ajaxSetup({async: true});
	
}



function showFriendMessage(ContainerDiv){
	
	 $.ajaxSetup({async: false});	

	 var param = "key="+getKey();
	 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/searchwithoutfriendquery",param,function(data){
		 
		for(var i=0; i<data.length; i++){
		
				var obj = data[i];
				var param = "key="+getKey();
				var JSONString = JSON.stringify(obj); 
				var idCom = $.parseJSON(JSONString)['_id'];
				$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
			 		var param="idUser="+idUser+"&"+"key="+getKey()+"&"+"idCom="+idCom;

					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/checklikeincomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
						
						if(codeHTTP == "200"){
							var template = '<div class="page-header Coms"><h4>{{name}} {{firstName}} <small>{{date}}</small></h4><pre>{{comment}}</pre> <button name="{{_id}}" class="btn btn-mini btn-info likeButton" type="button">Unlike</button></div>';
							var output = Mustache.render(template, obj);
							ContainerDiv.append(output).hide().fadeIn(1);
						}	
						else if(codeHTTP == "400") {
							var template = '<div class="page-header Coms"><h4>{{name}} {{firstName}} <small>{{date}}</small></h4><pre>{{comment}}</pre> <button name="{{_id}}" class="btn btn-mini btn-info likeButton" type="button">Like</button></div>';
							var output = Mustache.render(template, obj);
							ContainerDiv.append(output).hide().fadeIn(1);	

						}
					});
				});
		}
		
		$('.likeButton').click( function(){
		 	var bouton = $(this);
			
			if(bouton.html()=="Like"){
		  	 	var key = getKey();
			   	var idCom = $(this).attr("name");
				var param = "key="+key;
	  			 $.ajaxSetup({async: false});
		
				 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
			
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
		 			var param="idUser="+idUser+"&"+"key="+key+"&"+"idCom="+idCom;
					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/addlikecomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
					
						if(codeHTTP == "200"){
							bouton.html("Unlike");
						}
				 	});
				 });
			}
			else if (bouton.html()=="Unlike"){
		  	 	var key = getKey();
			   	var idCom = $(this).attr("name");
				var param = "key="+key;
	  			 $.ajaxSetup({async: false});
		
				 $.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/idfromkeysession",param,function(data){
			
					var JSONString = JSON.stringify(data); 
					var idUser = $.parseJSON(JSONString).ID; 
		 			var param="idUser="+idUser+"&"+"key="+key+"&"+"idCom="+idCom;
					$.getJSON("http://localhost:8080/MuruganathanNadarajahSocial/user/removelikecomment",param,function(data){
						var JSONString = JSON.stringify(data); 
						var codeHTTP = $.parseJSON(JSONString).CodeHTTP;
					
						if(codeHTTP == "200"){
							bouton.html("Like");
						}
				 	});
				 });
			}
			
		});
		
	 });
	$.ajaxSetup({async: true});
	
}

