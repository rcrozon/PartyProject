<?php

class TicketInfo extends AppModel{
 public $validate = array(
        'coordQrX' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre pseudo n'est pas valide",
                    'readonly' => 'readonly',

                'disabled' => 'disabled'
            )),
        'coordQrY' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre pseudo n'est pas valide",
                    'readonly' => 'readonly',

                'disabled' => 'disabled'
            )),
        'coordTextY' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre pseudo n'est pas valide",
                    'readonly' => 'readonly',

                'disabled' => 'disabled'
            )),
        'coordTextX' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Votre pseudo n'est pas valide",
                    'readonly' => 'readonly',

                'disabled' => 'disabled'
            )));
}