import { SplashScreen } from "@/src/screens/splash/index";
import { useAppFonts } from "@/src/hooks/fonts/use-fonts";
import { Redirect } from "expo-router";
import { useEffect, useState } from "react";


export default function Index (){

    const [isLoading, setIsLoading] = useState(true);
    const fontsLoaded = useAppFonts();

  useEffect(() => {
    if (fontsLoaded) {
      setTimeout(() => setIsLoading(false), 4000);
    }
  }, [fontsLoaded]);

  if (isLoading || !fontsLoaded) {
    return <SplashScreen />;
  }

  return <Redirect href="/auth/login" />;

};