<?php
class Tarif extends AppModel{
     
    public $validate = array(
        'label' => array(
            array(
                'rule' => array('custom','/([\w.-]+)+[\w+.-]/'),
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your label is not valid"
            ),
        ),
        'price' => array(
            array(
                'rule' => 'numeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your price is not valid"
            )
        )
    );
}