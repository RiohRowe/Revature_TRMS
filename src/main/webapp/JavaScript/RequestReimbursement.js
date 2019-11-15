let etypeE = document.getElementById("EventTypeSelect");
let costE = document.getElementById("inputCost1");
let ProjReimb = document.getElementById("projReimb");

var updatepr = function updateProjReimb()
{
	if(etypeE.options[etypeE.selectedIndex].value == "Certification" )
	{
		ProjReimb.value = costE.value * 1;
	}
	else if(etypeE.options[etypeE.selectedIndex].value == "Tech Training" )
	{
		ProjReimb.value = costE.value * 0.9;
	}
	else if(etypeE.options[etypeE.selectedIndex].value == "University Course" )
	{
		ProjReimb.value = costE.value * 0.8;
	}
	else if(etypeE.options[etypeE.selectedIndex].value == "Certification Prep" )
	{
		ProjReimb.value = costE.value * 0.75;
	}
	else if(etypeE.options[etypeE.selectedIndex].value == "Seminar" )
	{
		ProjReimb.value = costE.value * 0.6;
	}
	else if(etypeE.options[etypeE.selectedIndex].value == "other" )
	{
		ProjReimb.value = costE.value * 0.3;
	}
}

etypeE.addEventListener("change", updatepr);
costE.addEventListener("change", updatepr); 

