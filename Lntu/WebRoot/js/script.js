var login1 = false;
var login2 = false;
function xuehao(form) {//��֤�û�����Ϊ��
	var id = document.getElementById('s1');
	var iid = document.getElementById('xh');
	 if(iid.value=='') {
        id.style.display='block'; 
        login1 = false;
    }else{
    	login1 = true;
    	id.style.display='none'; 
    }
}
function password() {//��֤���벻Ϊ��
	var ps = document.getElementById('s2');
	var ips = document.getElementById('ps');
	if(ips.value=='') {
        ps.style.display='block'; 
        login1 = false;
    }else{
    	login2 = true;
    	ps.style.display='none'; 
    }
}
function login() {
	var id = document.getElementById('s1');
	var iid = document.getElementById('xh');
	 if(iid.value=='') {
        id.style.display='block'; 
        login1 = false;
    }else{
    	login1 = true;
    	id.style.display='none'; 
    }
	 var ps = document.getElementById('s2');
		var ips = document.getElementById('ps');
		if(ips.value=='') {
	        ps.style.display='block'; 
	        login1 = false;
	    }else{
	    	login2 = true;
	    	ps.style.display='none'; 
	    }
	if(login1&&login2){
		document.loginform.submit();
	}
}
