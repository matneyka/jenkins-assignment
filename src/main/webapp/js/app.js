window.onload = function () {
	loadLogin();
	$('#toLogin').on('click',loadLogin);
	$('#toRegister').on('click',loadRegister);
	$('#toLogout').on('click',logout);
}

// Logging In
function loadLogin() {
	// console.log('in loadLogin()');
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			$('#content').html(xhr.responseText);
			loadLoginInfo();
			$('#toRegister').show();
			$('#toLogin').show();
			
			$('#toHome').hide();
			$('#toLogout').hide();
		}
	}
	
	xhr.open('GET','login.view',true);
	xhr.send();
}

function loadLoginInfo() {
	// console.log('in loadLoginInfo()');
	
	$('#login').on('click',login);
}

function login() {
	// console.log('in login()');
	
	let username = $('#username').val();
	let password = $('#password').val();

	let toSend = [username, password];
	let json = JSON.stringify(toSend);

	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let user = JSON.parse(xhr.responseText);
			
			if(user == null) {
				// console.log('Invalid');
				$('#invalidCredentials').addClass('alert alert-danger');
				$('#invalidCredentials').html('Invalid Login Credentials');
			} else {
				// console.log('Successful,');
				
				$('#toHome').on('click',() => loadDashboard(user,'all'));
				loadDashboard(user,'all');
			}
		}
	}
	
	xhr.open('POST','login',true);
	xhr.setRequestHeader('Content-type','application/json');
	xhr.send(json);
}

// Logging out
function logout() {
	// console.log('in logout()');
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			// console.log('Session invalidated!');
			loadLogin();
		}
	}
	
	xhr.open('GET','logout',true);
	xhr.send();
}

// Registering
function loadRegister() {
	// console.log('in loadRegister()');
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			$('#content').html(xhr.responseText);
			loadRegisterInfo();
			$('#toRegister').show();
			$('#toLogin').show();
			
			$('#toHome').hide();
			$('#toLogout').hide();
		}
	}
	
	xhr.open('GET','register.view',true);
	xhr.send();
}

function loadRegisterInfo() {
	// console.log('in loadRegisterInfo()');
	
	$('#register').on('click',register);
}

function register() {
	// console.log('in register()');
	
	let username = $('#username').val();
	let password = $('#password').val();
	let firstname = $('#firstname').val();
	let lastname = $('#lastname').val();
	let email = $('#email').val();

	// TODO: add javascript to verify values here (before toSend)
	let toSend;
	toSend = [username, password,firstname,lastname,email];
	let json = JSON.stringify(toSend);

	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let user = JSON.parse(xhr.responseText);
			
			if(user == null) {
				// console.log('Invalid');
				$('#invalid').addClass('alert alert-danger');
				$('#invalid').html('Unable to create user');
			} else {
				// console.log('Successful');
				loadLogin();
			}
		}
	}
	
	xhr.open('POST','register',true);
	xhr.setRequestHeader('Content-type','application/json');
	xhr.send(json);
}

function filterUsername(username) {
	return 0;

}

function filterEmail(email) {
	return 0;
}


// Dashboard - viewing requests
function loadDashboard(user,version) {
	// console.log('in loadDashboard()');
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			$('#content').html(xhr.responseText);
			$('#toLogin').hide();
			$('#toRegister').hide();
			$('#toHome').show();
			$('#toLogout').show();
			loadReimbursementTable(user,version);
			loadRequestChoices(user,version);
		}
	}
	
	xhr.open('GET','dashboard.view',true);
	xhr.send();
}

function loadReimbursementTable (user,version) {
	// console.log('in loadReimbursementTable()');
	
	$('table tbody').html('Loading...');
	
	let json = JSON.stringify(version);
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let values = JSON.parse(xhr.responseText);
			let reimbursements = values[0];
			let humanReadableValues = values[1];
			
			$('table tbody').html('');
			
			if(reimbursements.length > 0) {
				let i = 0;
				reimbursements.forEach((reimbursement) => {
					let id = reimbursement.id;
					reimbursement.amount = Number.parseFloat(reimbursement.amount).toFixed(2);
					let amount = reimbursement.amount;
					let submitted = dateToString(new Date(reimbursement.submitted));
					let resolved = 	
						(reimbursement.resolved != null) ? dateToString(new Date(reimbursement.resolved)) : '-';
					reimbursement.authorName = humanReadableValues[i][2];
					let author = reimbursement.authorName;
					reimbursement.resolver = humanReadableValues[i][3];
					let resolver = (reimbursement.resolver == '') ? '-' : reimbursement.resolver;
					reimbursement.status = humanReadableValues[i][1];
					let status = reimbursement.status;
					reimbursement.type = humanReadableValues[i][0];
					let type = reimbursement.type; 
					
					let markup = `
				<tr>
                    <td>${id}</td>
                    <td>${amount}</td>
                    <td>${type}</td>
                    <td>${status}</td>
                    <td>${submitted}</td>
                    <td>${resolved}</td>
                    <td>${author}</td>
                    <td>${resolver}</td>
                    <td class="text-center"><button type="button" class="btn btn-primary" id="${id}">View</button></td>
                </tr>`;
					
					$('table tbody').append(markup);
					loadUpdateRequestInfo(user,reimbursement,version);
					i++
				});
			}
		}
	}
	
	xhr.open('POST','getReimbursements',true);
	xhr.setRequestHeader('Content-type','application/json');
	xhr.send(json);
}

function dateToString(date){
	// console.log('in dateToString()');
	
	return date.getMonth() + '/' + date.getDate() + '/' + date.getFullYear();
}

function loadRequestChoices(user) {
	// console.log('in loadRequestChoices()');
	
	$('#addRequest').on('click',() => loadAddRequest(user));
	$('#filter').on('change',() => loadFilterBy(user));
}

function loadFilterBy(user) {
	// console.log('in loadFilterBy()');
	
	let version = $('#filter').val();
	loadDashboard(user,version);
}

//Adding requests
function loadAddRequest(user) {
	// console.log('in loadAddRequest()');

	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			$('#content').html(xhr.responseText);
			$('#toLogin').hide();
			$('#toRegister').hide();
			$('#toHome').show();
			$('#toLogout').show();
			
			loadAddRequestInfo(user);
		}
	}
	
	xhr.open('GET','addrequest.view',true);
	xhr.send();
}

function loadAddRequestInfo(user) {
	// console.log('in loadAddRequestInfo()');
	
	$('#add').prop("disabled",true);
	$('#add').on('click',() => addRequest(user));
	$('#amount').on('change',checkNumber)
}

function checkNumber() {
	let amount = $('#amount').val();
	let number = /(\d+\.\d*)|(\d*\.d+)|(\d)/;
	
	if(amount.match(number)) {
		$('#add').prop("disabled",false);
	} else {
		$('#add').prop("disabled",true);
	}
}

function addRequest(user) {
	// console.log('in addRequest()');

	let type = $('#type').val();
	let amount = $('#amount').val();

	// TODO: add javascript to verify values here (before toSend)
	let toSend = [type,amount];
	let json = JSON.stringify(toSend);

	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let reimb = JSON.parse(xhr.responseText);
			
			if(reimb == null) {
				// console.log('Invalid');
			} else {
				// // console.log('Successful');
				loadDashboard(user,'all');
				// console.log('AfterDashboard');
			}
		}
	}
	
	xhr.open('POST','addrequest',true);
	xhr.setRequestHeader('Content-type','application/json');
	xhr.send(json);
}

// Updating requests

function loadUpdateRequestInfo(user,reimb,version) {
	// console.log('in loadUpdateRequestInfo()');
	
	$(`#${reimb.id}`).on('click',() => loadUpdateRequest(user,reimb,version));
}

function loadUpdateRequest(user,reimb,version) {
	// console.log('in loadUpdateRequest()');
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			$('#content').html(xhr.responseText);
			$('#toLogin').hide();
			$('#toRegister').hide();
			$('#toHome').show();
			$('#toLogout').show();
			
			loadRequestInfo(user,reimb,version);
		}
	}
	
	xhr.open('GET','request.view',true);
	xhr.send();
}

function loadRequestInfo(user,reimb,version) {
	// console.log('in loadRequestInfo()');

	$('#back').on('click',() => loadDashboard(user,version));
	$('#id').html(`Reimbursement ${reimb.id}`);
	$('#amount').html(reimb.amount);
	$('#type').html(reimb.type);
	$('#status').html(reimb.status);
	$('#submitted').html(dateToString(new Date(reimb.submitted)));
	$('#author').html(reimb.authorName);
	$('#resolved').html((reimb.resolved != null) ? dateToString(new Date(reimb.resolved)) : '-');
	$('#resolver').html((reimb.resolver == '') ? '-' : reimb.resolver);
	
	if(user.userRole == 'FinancMngr' && reimb.resolver == 0 && user.userId == reimb.author) {
		$('#managerOptions').html(`
				<div class="alert alert-warning">Unable to edit own request</div>
				`);
		
	} else if(user.userRole == 'FinancMngr' && reimb.resolver == 0){
		$('#managerOptions').html(`
				<div class="my-1 mt-2">
					<button type="button" class="btn btn-primary mr-sm-2" id="approve">Approve</button>
					<button type="button" class="btn btn-primary" id="deny">Deny</button>
				</div>
			`);
		
		$('#approve').on('click',() => updateRequest(user,reimb,version,'approve'));
		$('#deny').on('click',() => updateRequest(user,reimb,version,'deny'));
	} else {
		$('#managerOptions').html('');
	}
}

function updateRequest(user,reimb,version,approval) {
	// console.log('in updateRequest()');
	
	let toString = [reimb,approval]
	let json = JSON.stringify(toString);
	
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4 && xhr.status == 200) {
			response = JSON.parse(xhr.responseText);
			// console.log(response);
			
			if(response != null){
				reimb = response[0];
				let humanReadableValues = response[1];
				
				reimb.status = humanReadableValues[1];
				reimb.type = humanReadableValues[0];
				reimb.authorName = humanReadableValues[2];
				reimb.resolver = humanReadableValues[3];
				
				loadRequestInfo(user,reimb,version);
			} else {
				loadRequestInfo(user,reimb,version);
				$('#managerOptions').append('Cannot update own request');
			}
		}
	}
	
	xhr.open('POST','updaterequest',true);
	xhr.setRequestHeader('Content-type','application/json');
	xhr.send(json);
}

