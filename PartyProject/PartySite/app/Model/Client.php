<?php
class Client extends AppModel{


     
    public $validate = array(
        'username' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre pseudo n'est pas valide"
            ),
            array(
                'rule' => 'isUnique',
                'message' => 'This username is already taken'
            )
        ),
        'mail' => array(
            array(
                'rule' => 'email',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "mail not valid"
            ),
            array(
                'rule' => 'isUnique',
                'message' => 'This mail is already taken'
            )
        ),
        'password' => array(
            'rule' => 'notEmpty',
            'message' => "You have to enter a password",
            'allowEmpty' => false
        ),
        'first_name' => array(
            'rule' => 'notEmpty',
            'message' => "You have to enter a firstname",
            'allowEmpty' => false
        ),
        'last_name' => array(
            'rule' => 'notEmpty',
            'message' => "You have to enter a lastname",
            'allowEmpty' => false
        )
    );
 
}