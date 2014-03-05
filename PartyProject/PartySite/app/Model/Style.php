<?php
class Style extends AppModel{
     
    public $validate = array(
        'name' => array(
            array(
                'rule' => array('custom','/([\w.-]+)+[\w+.-]/'),
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your style name is not valid"
            ),
            array(
                'rule' => 'isUnique',
                'message' => 'This style is already taken'
            )
        )
    );
}