import { SplashScreen } from "@/src/screens/splash/index";
import { Montserrat_700Bold, useFonts } from '@expo-google-fonts/montserrat';
import { Redirect } from "expo-router";
import { useEffect, useState } from "react";


export default function Index (){

    const [Loading,setLoading] = useState(true);
    const [fontsLoaded] = useFonts({Montserrat_700Bold});

    useEffect(() => {
      if (fontsLoaded) {
          setTimeout(() => setLoading(false), 3000);
      }
    }, [fontsLoaded]);

    if (Loading || !fontsLoaded) {
        return <SplashScreen />;
    }

   
    //   Login
     return <Redirect href="/auth/login" />;

};