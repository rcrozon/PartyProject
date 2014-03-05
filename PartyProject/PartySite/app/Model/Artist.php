<?php
class Artist extends AppModel{
     
    public $validate = array(
        'name' => array(
            array(
                'rule' => array('custom','/([\w.-]+)+[\w+.-]/'),
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your artist name is not valid"
            ),
            array(
                'rule' => 'isUnique',
                'message' => 'This artist is already taken'
            )
        )
    );
}