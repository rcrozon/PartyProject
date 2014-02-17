<?php
class Concert extends AppModel{
     
    public $validate = array(
    	'name_concert' => array(
            array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your party name is not valid"
            ),
        ),
        'location' => array(
        	array(
                'rule' => 'alphanumeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format is not valid"
            )
        ),
        'nb_seats' => array(
        	array(
                'rule' => 'numeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Please enter the number of seats"
            )
        ),
        /*'image' => array(
        	array(
                'rule' => array('extension', array('gif', 'jpeg', 'png', 'jpg')),
                'message' => "The extension of your background image is not valid."
            )
        ),*/
        'start_datetime' => array(
        ),
        'end_datetime' => array(
        )
    );
}