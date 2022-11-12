# DNBotLink v0.0.6-dev

Only available on BUILDS version of the DreamNetwork

#### Start the Bot

To Start the Bot, you just have to past your Bot Token in the `token` file (``./addons/DNBotLink/token``) and start the DreamNetwork !
The default prefix is `d!`, you can mention the Bot to get it. You can change the prefix if you want ! (see the config command)

---

## Console Commands:

> `server <start|stop> <service> [<server|proxy>]` - Start and Stop a DN Service
> 
> `server stop dynamic <service-ID>` - Stop a DYNAMIC DN Service

> `clients see` - See all Online Services on your DreamNetwork

> `autostart <add|remove> <service> [<server|proxy>]`

---

## Bot Commands:

> `start <service> [<server|proxy>]` - Start a DN Service

> `stop <service> [<server|proxy>]` - Strop a DN Service
> 
> `stop dynamic <service>`

> `link <service> [<server|proxy>]` - Link a DN Service and a Discord Channel

> `list` - See all DN Services

> `config <key> <value>` (`config` to see all) - Configure the Bot


## On linked Discord Channels

* If the channel is linked with a Service (Server and Proxy), you will receive the console in the channel
* If the channel is linked with a Server, you will also be able to send command by sending a message in the channel, just type the command !

---