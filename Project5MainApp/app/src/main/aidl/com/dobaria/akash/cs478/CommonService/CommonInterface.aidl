// CommonInterface.aidl
package com.dobaria.akash.cs478.CommonService;

// Declare any non-default types here with import statements

interface CommonInterface {

 String monthlyCash(int year);

 String dailyCash(int day, int dmonth, int year, int working_days);

 String yearlyAvg(int year);

}
