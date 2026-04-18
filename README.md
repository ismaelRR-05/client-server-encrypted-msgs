# Implementación de protocolo de envio de mensajes cifrados mediante TLS.

- Tanto el cliente como el servidor deben tener un certificado valido, de extensión .p12
- En cada script se debe especificar la ruta en la que está dicho archivo certificado (por defecto está en la misma carpeta)
  - Para acceder al mismo, cada script necesita la contraseña para descifrar ese mismo certificado. En el caso del certificado dado, la contraseña (incluida en los scripts) es miPassword123.
- El cliente tiene configurada como dirección de HOST "localhost". Si se desea, se puede cambiar y poner la dirección IP deseada.

Al seguir una estructura cliente-servidor, hay que arrancar el servidor primero y luego el cliente.

El servidor recibe del cliente un mensaje en texto plano, del cual intercambia las vocales con la siguiente correspondencia:
- "a" -> 4
- "e" -> 3
- "i" -> 1
- "o" -> 0
- "u" -> 6

Seguidamente, el servidor devuelve el texto modificado

Requisitos: ambos scripts han sido probados bajo la siguiente version de openjdk: 
openjdk version "25.0.2" 2026-01-20

