function displayRequests(RequestJsonArray, orderedList, div0, unique )
{
  for(let r of RequestJsonArray)
  {

//    	  <button id="ViewRequestMine1" class="btn btn-primary" type="button" data-toggle="collapse"
      // data-target="#MyRequest1">
//          MyRequest(1)
//        </button>

    let newListItem = document.createElement('li');
    let newButtonItem = document.createElement('button');
    newButtonItem.id = "ViewRequestMine"+r["requestId"];
    newButtonItem.className = 'btn btn-primary';
    newButtonItem.type = "button";
    newButtonItem.dataset.toggle = "collapse"
    newButtonItem.dataset.target = "#myRequest"+r["requestId"];
    newButtonItem.innerHTML = "MyRequest(" + r["requestId"] + ")";
    newListItem.appendChild(newButtonItem);
    orderedList.appendChild(newListItem);

    let div1 = document.createElement('div');
    div1.id = 'myRequest'+r["requestId"];
    div1.className = 'RequestDisplay collapse';
    div0.appendChild(div1);
    let div2 = document.createElement('div');
    div2.id = 'RequestInfoOuterDiv';
    div1.appendChild(div2);
    let div3 = document.createElement('div');
    div3.className = 'RequestinfoDiv';
    div2.appendChild(div3);

    let p1 = document.createElement('p');
    p1.append("STATUS:");
    let span1 = document.createElement('span');
    span1.id = "urgentStatus";
    span1.className = "pnb";
    if(r["urgent"])
    {
      span1.innerHTML = "urgent";
    }
    else
    {
      span1.innerHTML = "not urgent";
    }
    p1.appendChild(span1);
    let br1 = document.createElement('br');
    p1.appendChild(br1);
    p1.append("Request Made on <");
    let span2 = document.createElement('span');
    span2.id = "MakeDate";
    span2.className = "pnb";
    span2.innerHTML = r["cDate"];
    p1.appendChild(span2);
    p1.append("> at <");
    let span3 = document.createElement('span');
    span3.id = "MakeTime";
    span3.className = "pnb";
    span3.innerHTML = r["creationTime"];
    p1.appendChild(span3);
    p1.append(">.");
    let br2 = document.createElement('br');
    p1.appendChild(br2);
    p1.append("By:");
    let span4 = document.createElement('span');
    span4.id = "EmployeeName";
    span4.className = "pnb";
    span4.innerHTML = r["employeeId"];
    p1.appendChild(span4);
    p1.append(".");
    let br3 = document.createElement('br');
    p1.appendChild(br3);
    p1.append("APPROVED:");
    let span5 = document.createElement('span');
    span5.id = "ApprovalStatus";
    span5.className = "pnb";
    if(r["approved"] == true)
    {
      span5.innerHTML = "yes";
    }
    else if(r["approved"] == false)
    {
      span5.innerHTML = "denied/cancled";
    }
    else
    {
      span5.innerHTML = "pending";
    }
    p1.appendChild(span5);
    p1.className = "RequestInfoParagraph";
    div3.appendChild(p1);

    let div4 = document.createElement('div');
    div4.className = 'tac';
    let div5 = document.createElement('div');
    div5.className = 'RDACL dwidth-60 tacIb';
    div4.appendChild(div5);
    let h2_1 = document.createElement('h2');
    h2_1.className = 'RITBL';
    h2_1.innerHTML = "ApprovalChain:";
    div5.appendChild(h2_1);
    let ul1 = document.createElement('ul');
    for(let ac of r["approvalChain"])
    {
      let li1 = document.createElement('li');
      li1.className="RequestInfoParagraph";

      li1.append(ac['weight'] + ":");
        let span6 = document.createElement('span');
        span6.id = "ManagerName";
        span6.className = "pnb";
        span6.innerHTML = ac['employee_id'];
        li1.appendChild(span6);
        li1.append("has");
        let span7 = document.createElement('span');
        span7.id = "HasManagerApproved";
        span7.className = "pnb";
        if(ac["approved"] == true)
        {
          span7.innerHTML="approved";
        }
        else if(ac["approved"] == false)
        {
          span7.innerHTML ="denied";
        }
        else
        {
          span7.innerHTML = "not gotten";
        }
        li1.appendChild(span7);
        li1.append("this request.");
        li1.appendChild(document.createElement('br'));
        li1.append("TIMEOUT:");
        let span8 = document.createElement('span');
        span8.id = "HasManagerApprovalTimedOut";
        span8.className = "pnb";
        if(ac["hasTimedOut"])
        {
          span8.innerHTML="yes";
        }
        else
        {
          span8.innerHTML = "No";
        }
        li1.appendChild(span8);
        li1.append(". (");
        let span9 = document.createElement('span');
        span9.id = "TimeTillApproved";
        span9.className = "pnb";
        span9.innerHTML=ac['toTime'];
        li1.appendChild(span9);
        if(ac['employeeId'] == 1)
        {
          li1.append("Till Escelataion Email")
        }
        else
        {
          li1.append("Till Auto Approved)");
        }
        ul1.appendChild(li1);
    }
    div3.appendChild(div4);
    div5.appendChild(ul1);

    if(r['approved'])
    {
      let p2 = document.createElement('p');
      p2.appendChild(document.createElement('br'));
      let div6 = document.createElement('div');
      div6.className = "container-fluid";
      let div7 = document.createElement('div');
      div7.className = "row";
      let div8 = document.createElement('div');
      div8.className = "col-sm-3 RITBL";
      div8.append("Denial Reason:");
      div7.appendChild(div8);
      let div9 = document.createElement('div');
      div9.className = "col-sm-8";
      let div10 = document.createElement('div');
      div10.className = "card cardbody RequestInfoTB";
      div10.innerHTML = r["denialReason"];
      div9.append(div10);
      div7.appendChild(div9);
      div6.appendChild(div7);
      div3.appendChild(div6);
    }
    let p3 = document.createElement('p')
    p3.className = "RequestInfoParagraph";
    p3.append("Projected Reimbursement:");
    let span10 = document.createElement('span');
    span10.id = "ProjReimb";
    span10.className = "pnb";
    span10.innerHTML = "$" +r['projected_reimbursement'];
    p3.appendChild(span10);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Actual Reimbursement:");
    let span11 = document.createElement('span');
    span11.id = "ActualReimb";
    span11.className = "pnb";
    span11.innerHTML = "$" +r['reimbursement'];
    p3.appendChild(span11);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.appendChild(document.createElement('br'));
    p3.append("PreApproval File:");
    let b1 = document.createElement('button');
    b1.className = "btn btn-primary";
    b1.type = "button";
    let a1 = document.createElement('a');
    a1.style="color:snow";
    if(r['oRI']['preapprovalFile'] != null)
    {
    	a1.href = r['oRI']['preapprovalFile'];
    	a1.innerHTML = "File";
    	a1.download = r['oRI']['preapprovalFile'];
  	}
    else
    {
    	a1.innerHTML = "NO File";
    }
    b1.appendChild(a1);
    p3.appendChild(b1);
    p3.appendChild(document.createElement('br'));
    p3.append("Approval Type:");
    let span13 = document.createElement('span');
    span13.id = "PreAppType";
    span13.className = "pnb";
    span13.innerHTML = r['oRI']['preapprovalLevel'];
    p3.appendChild(span13);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.appendChild(document.createElement('br'));
    p3.append("Event Name:");
    let span14 = document.createElement('span');
    span14.id = "EventTitle";
    span14.className = "pnb";
    span14.innerHTML = r['event']['title'];
    p3.appendChild(span14);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Event Location:");
    let span15 = document.createElement('span');
    span15.id = "EventLocation";
    span15.className = "pnb";
    span15.innerHTML = r['event']['location'] + "<United States>";
    p3.appendChild(span15);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Cost:");
    let span16 = document.createElement('span');
    span16.id = "EventCost";
    span16.className = "pnb";
    span16.innerHTML = "$" +r['event']['cost'];
    p3.appendChild(span16);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Start Date:");
    let span17 = document.createElement('span');
    span17.id = "EventStartDate";
    span17.className = "pnb";
    span17.innerHTML = r['event']['sDate'];
    p3.appendChild(span17);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Days Off Required:");
    let span18 = document.createElement('span');
    span18.id = "DaysOfEvent";
    span18.className = "pnb";
    span18.innerHTML = r['event']['lengthInDays'];
    p3.appendChild(span18);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Passing Grade:");
    let span19 = document.createElement('span');
    span19.id = "PassingGrade";
    span19.className = "pnb";
    span19.innerHTML = r['event']['passingGrade'];
    p3.appendChild(span19);
    p3.append(".");
    p3.appendChild(document.createElement('br'));
    p3.append("Event Related Files:");
    let b2 = document.createElement('button');
    b2.className = "btn btn-primary";
    b2.type = "button";
    let a2 = document.createElement('a');
    a2.style="color:snow";
    if(r['oRI']['eventRelatedFile'] != null)
    {
    	a2.href = r['oRI']['eventRelatedFile'];
    	a2.innerHTML = "File";
    	a2.download = r['oRI']['eventRelatedFile'];
  	}
    else
    {
    	a2.innerHTML = "NO File";
    }
    b2.appendChild(a2);
    p3.appendChild(b2);
    p3.appendChild(document.createElement('br'));
    p3.appendChild(document.createElement('br'));
    div3.appendChild(p3);

    let div11 = document.createElement('div');
    div11.className = "container-fluid";
    let div12 = document.createElement('div');
    div12.className = "row";
    let div13 = document.createElement('div');
    div13.className = "col-sm-3 RITBL";
    div13.append('Event Descripton:');
    div12.appendChild(div13);
    let div14 = document.createElement('div');
    div14.className = "col-sm-8";
    let div15 = document.createElement('div');
    div15.className = "card cardbody RequestInfoTB";
    div15.append(r['eventDescription']);
    div14.appendChild(div15);
    div12.appendChild(div14);
    div11.appendChild(div12);

    let div16 = document.createElement('div');
    div16.className = "row";
    let div17 = document.createElement('div');
    div17.className = "col-sm-3 RITBL";
    div17.append('Reason For Attending:');
    div16.appendChild(div17);
    let div18 = document.createElement('div');
    div18.className = "col-sm-8";
    let div19 = document.createElement('div');
    div19.className = "card cardbody RequestInfoTB";
    div19.append(r['workJustification']);
    div18.appendChild(div19);
    div16.appendChild(div18);
    div11.appendChild(div16);
    div3.appendChild(div11);
    div3.appendChild(document.createElement('br'));

    let ul2 = document.createElement('ul');
    for(let ai of r["additionalInfo"])
    {
      console.log("AdditionalInfoThing")
      let li2 = document.createElement('li');
      let div20 = document.createElement('div');
      div20.className = 'row';
      let div21 = document.createElement('div');
      div21.className = 'col-sm-3 RITBULL';
      div21.append(ai['senderId'] +" asked " + ai['recipientId']);
      div20.appendChild(div21);
      let div22 = document.createElement('div');
      div22.className = "col-sm-8";
      let div23 = document.createElement('div');
      div23.className = "card cardbody RequestInfoTB";
      div23.innerHTML = ai['text_request'];
      div22.appendChild(div23);
      div20.appendChild(div22);
      li2.appendChild(div20);
      let div24 = document.createElement('div');
      div24.className = 'row';
      let div25 = document.createElement('div');
      div25.className = 'col-sm-3 RITBULL';
      div25.append('Employee responded:');
      div24.appendChild(div25);
      let div26 = document.createElement('div');
      div26.className = 'col-sm-8';
      let div27 = document.createElement('div');
      div27.className = 'card cardbody RequestInfoTB';
      div27.append(ai['text_info']);
      let b3 = document.createElement('button');
      b3.className = "btn btn-primary";
      b3.type = "button";
      let a3 = document.createElement('a');
      a3.style="color:snow";
      if(ai['file_info'] != null)
      {
      	a3.href = ai['file_info'];
      	a3.innerHTML = "File";
      	a3.download = ai['fileinfo'];
    	}
      else
      {
      	a3.innerHTML = "NO File";
      }
      b3.appendChild(a3);
//      let div28 = document.createElement('div');
//      div28.className = 'card cardbody RequestInfoTB';
//      div28.append(ai['file_info']);
      div26.appendChild(div27);
      div26.appendChild(b3);
      div24.appendChild(div26);
      li2.appendChild(div24);
      ul2.appendChild(li2);
    }
    div3.appendChild(ul2);
    div29 = document.createElement('div');
    div29.id = "Options"+unique+r['requestId'];
    div3.appendChild(div29);
  }
}


function GiveOptions(requests, unique)
{
	for(let r of requests)
	{
		if(r['approved'] == null)
		{	
			div1 = document.getElementById("Options"+unique+r['requestId']);
			b1 = document.createElement("button");
			b1.className="btn btn-primary";
			b1.innerHTML="Cancel Request"
				var cancelrequest = function cr()
			{
				let xhr2 = new XMLHttpRequest();
				let url2 = '/P1/Views/api/cancelrequest'+r['requestId'];
				xhr2.open('post', url2); 
				xhr2.send();
			}
			b1.addEventListener("click", cancelrequest);
			div1.appendChild(b1);
		}
	}
}






function generateMyRequests()
{
  let unique = "IMade"
  let xhr = new XMLHttpRequest(); //ready state is 0.
  let url = '/P1/Views/api/myrequests';

  let orderedList = document.getElementById('VRMButtons');
  let div0 = document.getElementById('MyRequestBlocks');
  xhr.onreadystatechange = function()
  {
    if(xhr.readyState === 4 && xhr.status === 200)
    {
      let requests = JSON.parse(xhr.responseText);
      displayRequests(requests, orderedList, div0, unique);
      GiveOptions(requests, unique);
    }

  }
  xhr.open('GET', url); //ready state is 1.
  xhr.send(); //ready state is 2.
}

function generateYourRequests()
{
  let unique2 = "YouMade"
  let xhr2 = new XMLHttpRequest(); //ready state is 0.
  let url2 = '/P1/Views/api/yourrequests';

  let orderedList2 = document.getElementById('VRYButtons');
  let div02 = document.getElementById('YourRequestBlocks');
  xhr2.onreadystatechange = function()
  {
    if(xhr2.readyState === 4 && xhr2.status === 200)
    {
      let requests2 = JSON.parse(xhr2.responseText);
      displayRequests(requests2, orderedList2, div02, unique2);
    }
  }
  xhr2.open('GET', url2); //ready state is 1.
  xhr2.send(); //ready state is 2.
}




window.onload = function()
{
	generateMyRequests();
	//generateYourRequests();
}


