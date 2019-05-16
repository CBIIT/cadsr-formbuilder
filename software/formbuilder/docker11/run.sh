#~/bin/sh
echo "Formbuilder in the directory: " $PWD
echo "tag: " $TAG
echo "BRANCH_OR_TAG: " $BRANCH_OR_TAG
git pull
if [ $tag != 'origin/master'  ] && [ $tag != 'master' ]; then
#  git checkout tags/$tag
#this is for branch checkout for now
	echo "checkout of" $tag
	git checkout $tag
fi
git pull

# Function to check if wildfly is up #
function wait_for_server() {
  until `/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> build application"

echo ant -file build.xml -Ddebug=${DEBUG} -DlogLevel=${LOG_LEVEL} -DCADSR.DS.USER=${CADSR_DS_USER} -DCADSR.DS.URL=${CADSR_DS_URL} -DTIER=${TIER} -DCADSR.DS.FL.USER=${CADSR_DS_FL_USER} -DCADSR.DS.PSWD=${CADSR_DS_PSWD} -DCADSR.DS.FL.PSWD=${CADSR_DS_FL_PSWD} build-all
ant -file build.xml -Ddebug=${DEBUG} -DlogLevel=${LOG_LEVEL} -DCADSR.DS.USER=${CADSR_DS_USER} -DCADSR.DS.URL=${CADSR_DS_URL} -DTIER=${TIER} -DCADSR.DS.FL.USER=${CADSR_DS_FL_USER} -DCADSR.DS.PSWD=${CADSR_DS_PSWD} -DCADSR.DS.FL.PSWD=${CADSR_DS_FL_PSWD} build-all


echo "=> starting wildfly in background"
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> deploying modules"
cp deployment-artifacts/jboss/formbuilder.ear /local/content/formbuilder/dist
cp deployment-artifacts/jboss/ojdbc7.jar /local/content/formbuilder/dist
cp deployment-artifacts/jboss/formbuilder_modules.cli /local/content/formbuilder/dist
cp deployment-artifacts/jboss/formbuilder_deploy.cli /local/content/formbuilder/dist


/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 --file=/local/content/formbuilder/dist/formbuilder_modules.cli

echo "=> reloading wildfly"
/opt/wildfly/bin/jboss-cli.sh --connect command=:reload

echo "=> Waiting for the server to reload"
wait_for_server

echo "=> deploying"
/opt/wildfly/bin/jboss-cli.sh -c --controller=localhost:9990 --file=/local/content/formbuilder/dist/formbuilder_deploy.cli

echo "=> shutting wildfly down"
/opt/wildfly/bin/jboss-cli.sh --connect command=:shutdown

echo "=> Let the server to shut down"
sleep 1m

echo "=> starting wildfly in foreground"
/opt/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 
