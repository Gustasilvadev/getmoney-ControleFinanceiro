import React, { useState } from "react";
import { View, TouchableOpacity, Image } from 'react-native';
import { useRouter } from 'expo-router';
import { styles } from "./style";

type MenuItem = {
  id: string;
  route: any;
  icon: any;
  isCenter?: boolean;
};

export const Footer = () => {
  const router = useRouter();
  const [activeTab, setActiveTab] = useState('home');

  const menuItems: MenuItem[] = [
    { 
      id: 'Home', 
      route: '/Home',
      icon: require('@/assets/images/iconHome.png')
    },
    { 
      id: 'HistoricoTransacoes', 
      route: '/HistoricoTransacoes',
      icon: require('@/assets/images/iconTransaction.png')
    },
    { 
      id: 'AdicionarTransacao', 
      route: '/AdicionarTransacao',
      icon: require('@/assets/images/iconAdd.png'),
      isCenter: true 
    },
    { 
      id: 'Graficos', 
      route: '/Graficos',
      icon: require('@/assets/images/iconChart.png')
    },
    { 
      id: 'Configuracao', 
      route: '/Configuracao', 
      icon: require('@/assets/images/iconConfig.png')
    },
  ];

  const handlePress = (item: MenuItem) => {
    setActiveTab(item.id);
    router.push(item.route);
  };

  return (
    <View style={styles.footerContainer}>
    
      <View style={styles.footer}>
      
        <View style={styles.topCurve} />
        
        {menuItems.map((item: MenuItem) => (
          <TouchableOpacity
            key={item.id}
            style={[
              styles.tab,
              item.isCenter && styles.centerTab,
              activeTab === item.id && styles.activeTab
            ]}
            onPress={() => handlePress(item)}
          >
            <Image 
              source={item.icon}
              style={[
                styles.icon,
                item.isCenter && styles.centerIcon,
                activeTab === item.id && styles.activeIcon
              ]}
              resizeMode="contain"
            />

          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
}