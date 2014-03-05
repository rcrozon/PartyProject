<?php
require('../webroot/encrypt/encrypt.php');

App::uses('AbstractPasswordHasher', 'Controller/Component/Auth');

class CustomPasswordHasher extends AbstractPasswordHasher {
    public function hash($password) {
    	$mcrypt = new MCrypt();
        $password = 'evreux';
    	$encrypted = $mcrypt->encrypt($password);

        	 	return  $encrypted;
      }

    public function check($password, $hashedPassword) {
    	$mcrypt = new MCrypt();
    	$encrypted = $mcrypt->encrypt($password);    	
        return $encrypted === $hashedPassword;
    }
}