import React, { useState } from "react";
import { Ionicons } from '@expo/vector-icons';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { useRouter } from 'expo-router';
import { styles } from "./style";

type MenuItem = {
  id: string;
  route: any;
  icon: any;
  label: string;
  isCenter?: boolean;
};

export const Footer = () => {
  const router = useRouter();
  const [activeTab, setActiveTab] = useState('home');

  const menuItems: MenuItem[] = [
    { 
      id: 'home', 
      route: '/home',
      icon: require('@/assets/images/iconHome.png'),
      label: 'Home' 
    },
    { 
      id: 'transactions', 
      route: '/transactions',
      icon: require('@/assets/images/iconTransaction.png'),
      label: 'Transações' 
    },
    { 
      id: 'add', 
      route: '/add',
      icon: require('@/assets/images/iconAdd.png'),
      label: 'Adicionar', 
      isCenter: true 
    },
    { 
      id: 'charts', 
      route: '/charts',
      icon: require('@/assets/images/iconChart.png'),
      label: 'Gráficos' 
    },
    { 
      id: 'settings', 
      route: '/settings', 
      icon: require('@/assets/images/iconConfig.png'),
      label: 'Configurações' 
    },
  ];

  const handlePress = (item: MenuItem) => {
    setActiveTab(item.id);
    router.push(item.route);
  };

  return (
    <View style={styles.footer}>

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

        <Text style={[
          styles.label,
          item.isCenter && styles.centerLabel,
          activeTab === item.id && styles.activeLabel
        ]}>
          {item.label}
        </Text>

        </TouchableOpacity>
      ))}
    </View>
  );
}