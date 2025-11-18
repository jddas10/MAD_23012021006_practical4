# Practical 4  

## 🎯 Aim  

Create an Android Alarm application by using **Service** & **BroadcastReceiver**.

---

## 📖 Description  

This practical demonstrates how to create a fully functional **Alarm Application** in Android using:

- **BroadcastReceiver** → Receives the alarm trigger  
- **Service** → Plays ringtone using MediaPlayer  
- **AlarmManager** → Schedules exact alarms  
- **PendingIntent** → Sends alarm trigger  
- **TimePickerDialog** → Allows user to select alarm time  
- **Calendar Class** → Handles time conversion  
- **SimpleDateFormat** → Displays selected alarm time  
- **MaterialCardView** → Used for UI layout  

The application provides the following features:  

1. Select a time using the TimePicker.  
2. Set Exact Alarm using AlarmManager.  
3. Alarm triggers using BroadcastReceiver.  
4. Alarm sound plays using Service.  
5. Cancel or Stop Alarm anytime.  

---

## 📸 Screenshots with Description  

| Screenshot | Description |  
|------------|-------------|  
| <p align="center"><img src="https://github.com/jddas10/MAD_23012021006_practical4/blob/master/p4_screenshots/Screenshot%202025-11-19%20033602.png" width="250"/></p> | The **Main Screen** displays the current time and a button to set an alarm. |
| <p align="center"><img src="[Screenshot 2025-11-19 033632](https://github.com/jddas10/MAD_23012021006_practical4/blob/master/p4_screenshots/Screenshot%202025-11-19%20033616.png)" width="250"/></p> | After selecting time, a **Toast message** appears and the **Cancel Alarm** button becomes visible. |
| <p align="center"><img src="[Screenshot 2025-11-19 033616](https://github.com/jddas10/MAD_23012021006_practical4/blob/master/p4_screenshots/Screenshot%202025-11-19%20033632.png)" width="250"/></p> | When the alarm triggers, the **BroadcastReceiver** activates and the **AlarmService** starts playing the ringtone. |
| <p align="center"><img src="[Screenshot 2025-11-19 033602](https://github.com/jddas10/MAD_23012021006_practical4/blob/master/p4_screenshots/Screenshot%202025-11-19%20033654.png)" width="250"/></p> | After pressing stop/cancel, the alarm sound is stopped and the service ends successfully. |

---

## 🛠 Components Used  

- **AlarmManager**  
- **PendingIntent**  
- **BroadcastReceiver**  
- **Service (MediaPlayer)**  
- **TimePickerDialog**  
- **Calendar Class**  
- **SimpleDateFormat**  
- **MaterialCardView**  
- **sendBroadcast()**  
- **startService() / stopService()**  
- **getSystemService()**  

---

## 📝 Required Permission  

```xml
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

## 🧪 Result  

The Android application was successfully created to demonstrate **Alarm Scheduling** using  
**BroadcastReceiver**, **Service**, and **AlarmManager**.  
The app allows users to **set an alarm, trigger it at the selected time, play ringtone using a service,**  
and also **cancel/stop the alarm** when required.  

All required components like **TimePickerDialog, Calendar, PendingIntent, MediaPlayer,  
sendBroadcast(), startService() and stopService()** were implemented successfully.


## 📘 Student Details

>  **Name:** *DAVE JAYDATT*  
>  **Enrollment:** *23012021006*  
>  **Batch:** *5IT-B-1*  
