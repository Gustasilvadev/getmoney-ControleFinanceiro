import { useRouter } from 'expo-router';

export const useNavigation = () => {
  
  const router = useRouter();

  const navigateTo = {
    login: () => router.push('/auth/login'),
    register: () => router.push('/auth/register'),
    home:() => router.push('/Home'),
    perfil: () => router.push('/Perfil'),
    metas: () => router.push('/Metas'),
    back: () => router.back(),
  };

  return navigateTo;
};