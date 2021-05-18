#!/bin/bash
PROPERTIESFILEPATH="src/test/resources/Jenkins/Config.properties"

if [[ $Url ]]; then
    sed -i'' -e "s,urlforenv=,urlforenv=$Url,g" $PROPERTIESFILEPATH
else
    echo "Missing URL for the environment."
    exit 1
fi

if [[ $Env ]]; then
    sed -i'' -e "s,environment=,environment=$Env,g" $PROPERTIESFILEPATH
else
    echo "Missing environment name."
    exit 1
fi

if [[ $TesterName ]]; then
    sed -i'' -e "s,testername=,testername=$TesterName,g" $PROPERTIESFILEPATH
else
    echo "Missing testername."
    exit 1
fi

if [[ $AppiumHub ]]; then
    sed -i'' -e "s,appiumhub=,appiumhub=$AppiumHub,g" $PROPERTIESFILEPATH
else
    echo "Missing appiumhub."
    exit 1
fi

if [[ $RunMode == 'grid'  || $RunMode == 'selenoid' || $RunMode == 'zalenium' ]]; then
    sed -i'' -e "s,runmode=,runmode=$RunMode,g" $PROPERTIESFILEPATH
    if [[ $RemoteUrl ]]; then
        sed -i'' -e "s,remoteurl=,remoteurl=$RemoteUrl,g" $PROPERTIESFILEPATH
    else
        echo "Missing Remote URL."
        exit 1
    fi
else
    echo "Missing Run Mode for execution."
    exit 1
fi

if [[ $UseElk ]]; then
    sed -i'' -e "s,useelk=,useelk=$UseElk,g" $PROPERTIESFILEPATH
    if [[ $ElasticSearchUrl ]]; then
        sed -i'' -e "s,elksuiteurl=,elksuiteurl=$ElasticSearchUrl,g" $PROPERTIESFILEPATH
    else
        echo "Missing ELK URL."
        exit 1
    fi
else
    sed -i'' -e "s,useelk=,useelk=no,g" $PROPERTIESFILEPATH
fi

if [[ $DeleteReports ]]; then
    sed -i'' -e "s,deleteoldreports=,deleteoldreports=$DeleteReports,g" $PROPERTIESFILEPATH
    if [[ $NumberOfDays ]]; then
        sed -i'' -e "s,numberofdays=,numberofdays=$NumberOfDays,g" $PROPERTIESFILEPATH
    else
        sed -i'' -e "s,numberofdays=,numberofdays=10,g" $PROPERTIESFILEPATH
    fi
else
    sed -i'' -e "s,deleteoldreports=,deleteoldreports=no,g" $PROPERTIESFILEPATH
fi

if [[ $OverRideReports ]]; then
    sed -i'' -e "s,overridereports=,overridereports=$OverRideReports,g" $PROPERTIESFILEPATH
else
    sed -i'' -e "s,overridereports=,overridereports=no,g" $PROPERTIESFILEPATH
fi

if [[ $TakeScreenShotForPass ]]; then
    sed -i'' -e "s,passedscreenshot=,passedscreenshot=$TakeScreenShotForPass,g" $PROPERTIESFILEPATH
else
    sed -i'' -e "s,passedscreenshot=,passedscreenshot=no,g" $PROPERTIESFILEPATH
fi

if [[ $RetryFailedTestCase ]]; then
    sed -i'' -e "s,retryfailedtestcases=,retryfailedtestcases=$RetryFailedTestCase,g" $PROPERTIESFILEPATH
else
    sed -i'' -e "s,retryfailedtestcases=,retryfailedtestcases=no,g" $PROPERTIESFILEPATH
fi

if [[ $SendMailAfterExecution ]]; then
    sed -i'' -e "s,sendmailafterexecution=,sendmailafterexecution=$SendMailAfterExecution,g" $PROPERTIESFILEPATH
    if [[ $MailServerName ]]; then
        sed -i'' -e "s,sendmailusing=,sendmailusing=$MailServerName,g" $PROPERTIESFILEPATH
    else
        echo "Missing Mail Server Name."
        exit 1
    fi
    if [[ $SenderEmailID ]]; then
        sed -i'' -e "s,sendersid=,sendersid=$SenderEmailID,g" $PROPERTIESFILEPATH
    else
        echo "Missing Sender's Email ID."
        exit 1
    fi
    if [[ $SenderPassword ]]; then
        sed -i'' -e "s,emailpassword=,emailpassword=$SenderPassword,g" $PROPERTIESFILEPATH
    else
        echo "Missing Sender's Password in Base64Encode."
        exit 1
    fi
    if [[ $ReceiverMailID ]]; then
        sed -i'' -e "s,receiversid=,receiversid=$ReceiverMailID,g" $PROPERTIESFILEPATH
    else
        echo "Missing Receiver's Email ID."
        exit 1
    fi
else
    sed -i'' -e "s,sendmailafterexecution=,sendmailafterexecution=no,g" $PROPERTIESFILEPATH
fi

rm src/test/resources/config/Config.properties
mv src/test/resources/Jenkins/Config.properties src/test/resources/config/Config.properties