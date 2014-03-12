<?php
class Concert extends AppModel{
     
    public $validate = array(
        'name_concert' => array(
            array(
                'rule' => array('custom','/([\w.-]+)+[\w+.-]/'),
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of your party name is not valid"
            ),
        ),
        'location' => array(
            array(
                'rule' => array('custom','/([\w.-]+)+[\w+.-]/'),
                'required' => true,
                'allowEmpty' => false, 
                'message' => "The format of the location is not valid"
            )
        ),
        'nb_seats' => array(
            array(
                'rule' => 'numeric',
                'required' => true,
                'allowEmpty' => false, 
                'message' => "Please enter the number of seats"
            )
        )
    );

    public function isUploadedFile($params) {
        $val = $params;
        if ((isset($val['error']) && $val['error'] == 0) ||
            (!empty( $val['tmp_name']) && $val['tmp_name'] != 'none')
        ) {
            return is_uploaded_file($val['tmp_name']);
        }
        return false;
    }
}