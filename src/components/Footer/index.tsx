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
      id: 'home', 
      route: '/home',
      icon: require('@/assets/images/iconHome.png')
    },
    { 
      id: 'transactions', 
      route: '/historyTransactions',
      icon: require('@/assets/images/iconTransaction.png')
    },
    { 
      id: 'add', 
      route: '/add',
      icon: require('@/assets/images/iconAdd.png'),
      isCenter: true 
    },
    { 
      id: 'charts', 
      route: '/charts',
      icon: require('@/assets/images/iconChart.png')
    },
    { 
      id: 'settings', 
      route: '/settings', 
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