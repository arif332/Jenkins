//
//  Author: Hari Sekhon
//  Date: 2021-04-30 15:25:01 +0100 (Fri, 30 Apr 2021)
//
//  vim:ts=2:sts=2:sw=2:et
//
//  https://github.com/HariSekhon/Jenkins
//
//  License: see accompanying Hari Sekhon LICENSE file
//
//  If you're using my code you're welcome to connect with me on LinkedIn and optionally send me feedback to help steer this or other code I publish
//
//  https://www.linkedin.com/in/HariSekhon
//

def call(String scriptPath, List<String> locks, int timeoutMinutes=60){
  // generate a list in this format
  //lock(extra: [[resource: 'lock1'], [resource: 'lock2']])
  List extraLocks = []
  for (lock in locks){
    echo "Acquiring Lock: $lock"
    extraLocks.append([resource: lock])
  }
  lock(extra: extraLocks, inversePrecedence: true){
    milestone label: "Milestone: Running script: $scriptPath"
    retry(2){
      timeout(time: timeoutMinutes, unit: 'MINUTES') {
        // external script needs to exist in the source repo, not the shared library repo
        sh "$scriptPath"
      }
    }
  }
}
