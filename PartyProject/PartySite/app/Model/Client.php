<?php
App::uses('CustomPasswordHasher', 'Controller/Component/Auth');

class Client extends AppModel{


// ...

public function beforeSave($options = array()) {
    if (isset($this->data[$this->alias]['password'])) {
        $passwordHasher = new CustomPasswordHasher();
        $this->data[$this->alias]['password'] = $passwordHasher->hash($this->data[$this->alias]['password']);
    }
    return true;
}
     
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
                'message' => 'Ce pseudo est déja pris'
            )
        ),
        'mail' => array(
            array(
                'rule' => 'email',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre email n'est pas valide"
            ),
            array(
                'rule' => 'isUnique',
                'message' => 'Cet email est déjà pris'
            )
        ),
        'password' => array(
            'rule' => 'notEmpty',
            'message' => "Vous devez entrer un mot de passe",
            'allowEmpty' => false
        ),
            'first_name' => array(
            'rule' => 'notEmpty',
            'message' => "Vous devez entrer un prénom",
            'allowEmpty' => false
        ),
                'last_name' => array(
            'rule' => 'notEmpty',
            'message' => "Vous devez entrer un nom",
            'allowEmpty' => false
        )
    );
 
}