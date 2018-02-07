package com.cs478.akash.cs478_proj2_akash_dobaria;


import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

/************************************************************************************************************
 * **********************************************************************************************************
 **                                                                                                        **
 **  2 classes are defined in this file.                                                                   **
 **                                                                                                        **
 **      1. CarDetails : object of this class is used to store all information of a car together           **
 **              Datamembers: company_name --> string --> to store company name of car image               **
 **                           image_path --> integer --> to store the resource id of the image             **
 **                           url --> string --> to store the url of the car menufacturer                  **
 **                           dealers --> ArrayList of Dealers_details --> to store dealers information    **
 **              Methods: createArrayList() --> no arguments --> return type: ArrayList<CarDetails>        **
 **                       --> used to create arraylist with all details                                    **
 **                                                                                                        **
 **      2. Dealers_details: to store details of the car dealer together. Used in CarDetails class.        **
 **              Datamembers: name --> string --> name of the dealer                                       **
 **                           address --> string --> address of the dealer                                 **
 **                           number --> string --> number of the dealer                                   **
 **                                                                                                        **
 ************************************************************************************************************
 ***********************************************************************************************************/

public class CarDetails {

    //Class data-members
    String company_name;
    Integer image_path;
    String url;
    ArrayList<Dealers_details> dealers;

    //constructor
    public CarDetails()
    {
        dealers = new ArrayList<Dealers_details>();
    }

    //method to store all data of the cars and dealers in one ArrayList
    public static ArrayList<CarDetails> createArrayList(){
        final ArrayList<CarDetails> car_list = new ArrayList<>();

        //details of image resources
        ArrayList<Integer> images = new ArrayList<Integer>(
                Arrays.asList(R.drawable.ferrari, R.drawable.bugatti, R.drawable.cadillac,
                    R.drawable.chevrolet, R.drawable.ford, R.drawable.jeep,
                    R.drawable.mercs, R.drawable.rangerover, R.drawable.tesla));

        //details of car companies
        ArrayList<String> company_names = new ArrayList<String>(
                Arrays.asList("Ferrari", "Bugatti", "Cadillac", "Chevrolet", "Ford", "Jeep", "Mercedes-Benz", "Range Rover", "Tesla")
        );

        //details of company websites
        ArrayList<String> urls = new ArrayList<String>(
                Arrays.asList("https://www.ferrari.com/en-US", "https://www.bugatti.com/home/","http://www.cadillac.com/",
                        "http://www.chevrolet.com/","https://www.ford.com/",
                        "https://www.jeep.com/","https://www.mbusa.com/mercedes/index",
                        "https://www.landroverusa.com/vehicles/range-rover/index.html","https://www.tesla.com/")
        );

        //detailes of car dealers' names
        ArrayList<ArrayList<String>> dealer_name = new ArrayList<ArrayList<String>>();
        ArrayList<String> dnf1 = new ArrayList<String>(Arrays.asList("McLaren Chicago Showroom","Continental AutoSports","Mancuso Motorsports"));
        ArrayList<String> dnb1 = new ArrayList<String>(Arrays.asList("Bentlley Gold Coast"));
        ArrayList<String> dnca1 = new ArrayList<String>(Arrays.asList("Greater Chicago Motors","Shirey Cadillac","Grossinger Cadillac"));
        ArrayList<String> dnch1 = new ArrayList<String>(Arrays.asList("Bredemann Chevrolet","Rogers Chevrolet","Kingdom Chevy","Chicago Motors Inc."));
        ArrayList<String> dnfo1 = new ArrayList<String>(Arrays.asList("Bredemann Ford in Glenview","Fox Ford Lincoln","Metro Ford Sales and Service"));
        ArrayList<String> dnj1 = new ArrayList<String>(Arrays.asList("Marino Chrysler Jeep Dodge","South Chicago Dodge Chrysler Jeep","Hawk Chrysler Dodge Jeep"));
        ArrayList<String> dnmb1 = new ArrayList<String>(Arrays.asList("Mercedes-Benz of Chicago","Loeber Motors","Mercedes-Benz of Westmont"));
        ArrayList<String> dnlr1 = new ArrayList<String>(Arrays.asList("Land Rover Chicago","Land Rover Hinsdale","Land Rover of Naperville"));
        ArrayList<String> dnt1 = new ArrayList<String>(Arrays.asList("Tesla"));

        dealer_name.add(dnf1);
        dealer_name.add(dnb1);
        dealer_name.add(dnca1);
        dealer_name.add(dnch1);
        dealer_name.add(dnfo1);
        dealer_name.add(dnj1);
        dealer_name.add(dnmb1);
        dealer_name.add(dnlr1);
        dealer_name.add(dnt1);

        //details of car dealers' address
        ArrayList<ArrayList<String>> dealer_address = new ArrayList<ArrayList<String>>();
        ArrayList<String> dnf2 = new ArrayList<String>(Arrays.asList("645 W Randolph St, Chicago, IL 60661","420 E Ogden Ave, Hinsdale, IL 60521","677 N Clark St, Chicago, IL 60654"));
        ArrayList<String> dnb2 = new ArrayList<String>(Arrays.asList("834 N Rush St, Chicago, IL-60611"));
        ArrayList<String> dnca2 = new ArrayList<String>(Arrays.asList("1850 N Elston Ave, Chicago, IL 60642","10125 S Cicero Ave, Oak Lawn, IL 60453","6900 McCormick Blvd, Lincolnwood, IL 60712"));
        ArrayList<String> dnch2 = new ArrayList<String>(Arrays.asList("1401 Dempster St, Park Ridge, IL 60068","2720 S Michigan Ave, Chicago, IL 60616","6603 S Western Ave, Chicago, IL 60636","2553 W Chicago Ave, Chicago, IL 60622"));
        ArrayList<String> dnfo2 = new ArrayList<String>(Arrays.asList("2038 Waukegan Rd, Glenview, IL 60025","2501 N Elston Ave, Chicago, IL 60647","6455 S Western Ave, Chicago, IL 60636"));
        ArrayList<String> dnj2 = new ArrayList<String>(Arrays.asList("5133 W Irving Park Rd, Chicago, IL 60641","7340 S Western Ave, Chicago, IL 60636","7911 W Roosevelt Rd, Forest Park, IL 60130"));
        ArrayList<String> dnmb2 = new ArrayList<String>(Arrays.asList("1520 W North Ave, Chicago, IL 60642","4255 Touhy Ave, Lincolnwood, IL 60712","200 E Ogden Ave, Westmont, IL 60559"));
        ArrayList<String> dnlr2 = new ArrayList<String>(Arrays.asList("1924 N Paulina St, Chicago, IL 60622","300 E Ogden Ave, Hinsdale, IL 60521","1559 W Ogden Ave, Suite A, Naperville, IL 60540"));
        ArrayList<String> dnt2 = new ArrayList<String>(Arrays.asList("1053 W Grand Ave, Chicago, IL 60642"));

        dealer_address.add(dnf2);
        dealer_address.add(dnb2);
        dealer_address.add(dnca2);
        dealer_address.add(dnch2);
        dealer_address.add(dnfo2);
        dealer_address.add(dnj2);
        dealer_address.add(dnmb2);
        dealer_address.add(dnlr2);
        dealer_address.add(dnt2);

        //details of car dealers' numbers
        ArrayList<ArrayList<String>> dealer_number = new ArrayList<ArrayList<String>>();
        ArrayList<String> dnf3 = new ArrayList<String>(Arrays.asList("(312) 635-6482","(630) 480-41443 ","(312) 624-8586"));
        ArrayList<String> dnb3 = new ArrayList<String>(Arrays.asList("(312) 280-4848"));
        ArrayList<String> dnca3 = new ArrayList<String>(Arrays.asList("(312) 280-9262","(800) 338-4553","(800) 989-0313"));
        ArrayList<String> dnch3 = new ArrayList<String>(Arrays.asList("(847) 655-1485","(312) 225-4300","(877) 921-8488","(773) 235-6500"));
        ArrayList<String> dnfo3 = new ArrayList<String>(Arrays.asList("(847) 510-5585","(888) 380-8568","(773) 776-7600"));
        ArrayList<String> dnj3 = new ArrayList<String>(Arrays.asList("(773) 777-2000","(773) 476-7800","(708) 366-1001"));
        ArrayList<String> dnmb3 = new ArrayList<String>(Arrays.asList("(312) 628-4500","(847) 675-1000","(630) 537-0313"));
        ArrayList<String> dnlr3 = new ArrayList<String>(Arrays.asList("(773) 227-3200","(630) 521-3426","(630) 300-5000"));
        ArrayList<String> dnt3 = new ArrayList<String>(Arrays.asList("(312) 733-9780"));

        dealer_number.add(dnf3);
        dealer_number.add(dnb3);
        dealer_number.add(dnca3);
        dealer_number.add(dnch3);
        dealer_number.add(dnfo3);
        dealer_number.add(dnj3);
        dealer_number.add(dnmb3);
        dealer_number.add(dnlr3);
        dealer_number.add(dnt3);

       //construct CarDetails object and add it in arraylist and return the final arraylist
        for(int i=0; i < images.size(); i++){

            //declare CarDetails object
            CarDetails car = new CarDetails();

            //get i^th car details and store in the "car" object
            car.image_path = images.get(i);
            car.company_name = company_names.get(i);
            car.url = urls.get(i);

            //for i^th car get details of all dealers and store it in the "car" object
            for(int j=0; j<dealer_name.get(i).size(); j++){
                Dealers_details dealer = new Dealers_details();
                dealer.name = dealer_name.get(i).get(j);
                dealer.address = dealer_address.get(i).get(j);
                dealer.number = dealer_number.get(i).get(j);
                car.dealers.add(j,dealer);
            }
            car_list.add(car);
        }

        return car_list;

    }

    public static class Dealers_details{
        public String name;
        public String address;
        public String number;
    }

}

