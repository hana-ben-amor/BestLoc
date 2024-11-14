Etape 1:
	1-Creer une base données nommée: locationdatabase sous votre SGBD Mysql
	2-Creer la table Position ayant comme attributs : idposition,longitude,latitude,numero,pseudo(varchar (30))
Etape 2:
	3-Creer le dossier nommée servicephp sous le dossier www ou autre (selon le SGBD) 
	4-Creer le fichier config.php sous servicephp contenant la configuration de la connexion:
		<?php
			$user="root";
			$mp="";
			$database="locationdatabase";
			$server="127.0.0.1";
			$port="3306";
		?>
	5-Creer le fichier get_all.php effectuant la requete :select * from Position comme suit:
	
	
<?php
require_once 'config.php';
// array for JSON response
$response = array();
$con= mysqli_connect($server,$user,$mp,$database,$port);
// get all Amis from  table
$result = mysqli_query($con,"SELECT *FROM Position") or die(mysqli_error());
// check for empty result
if (mysqli_num_rows($result) > 0) {
	 // success
    $response["success"] = 1;
    // looping through all results
    // Ami node
    $response["positions"] = array();
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $une_position = array();
        $une_position["idposition"] = $row["idposition"];
		$une_position["pseudo"] = $row["pseudo"];
		$une_position["numero"] = $row["numero"];
		$une_position["longitude"] = $row["longitude"];
		$une_position["latitude"] = $row["latitude"];
		  
        array_push($response["positions"], $une_position);
    }
    
} else {
    // no Ami found
    $response["success"] = 0;
    $response["message"] = "No position found";

}
// echo result
    echo json_encode($response);
?>
	6-Creer le fichier add_position.php effectuant la requete :insert into Position values (....,....,....);
	 NB:
	 $param=$_GET['nom_param'];
	 ou 
	 $param=$_POST['nom_param'];
	 ==> permet de recuperer des parametres dans un fichier php
	 
Etape 3:
	7- Tester votre fichier php depuis votre navigateur web sous windows ou linux
	8- Tester votre serveur php depuis votre navigateur du smartphone avec  l'IPv4 
	NB: *Vous devez ouvrir votre serveur à l'ecoute des autres ip (non pas de l'ip local seulement)
		*Permettre l'acces au dossier web du SGBD
	9- Tester vos fichiers php depuis le navigateur du smartphone 